package com.tmb.mediainfo.service;

import com.amazonaws.services.s3.AmazonS3;
import com.opencsv.CSVWriter;
import com.tmb.mediainfo.model.MediaInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Service
public class MetaDataExtractor {

    @Autowired
    AmazonS3 s3Client;
    @Autowired
    MediaInfoService mediaInfoService;

    public void extractMetaData() throws IOException {
        String[] header = {"footageId", "width", "height", "Duration", "Orientation", "MIME type", "Update Query", "Error"};

        List<String[]> list = new ArrayList<>();
        list.add(header);

        String line = "";
        String splitBy = ",";
        String[] cols = null;
        BufferedReader br = new BufferedReader(new FileReader("src/main/missing_meta_data.csv"));
        // Skipping  first row if csv file have header
        br.readLine();

        while ((line = br.readLine()) != null) {
            try {
                cols = line.split(splitBy);
                URL s3Url = s3Client.getUrl(cols[1], cols[2]);
                MediaInfo info = mediaInfoService.getMediaInfo(s3Url.toString());
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("update footage set height='").append(info.getVideoHeight()).append("' , width='").append(info.getVideoWidth()).append("' , orientation='").append(info.getOrientation()).append("', duration='").append(Math.round(info.getDuration())).append("' where id='").append(cols[0]).append("';").
                        append(" update file_asset set mime_type='").append(info.getMimeType()).append("' where id='").append(cols[0]).append("';");
                String[] rowData = {cols[0], String.valueOf(info.getVideoWidth()), String.valueOf(info.getVideoHeight()), String.valueOf(Math.round(info.getDuration())), info.getOrientation(), info.getMimeType(), stringBuilder.toString()};
                list.add(rowData);
            } catch (Exception e) {
                String[] rowData = {cols[0], null, null, null, null, null, null, e.getMessage()};
                list.add(rowData);
            }
        }
        writeCSVFile(list);
    }

    private void writeCSVFile(List<String[]> list) throws IOException {

        try (CSVWriter writer = new CSVWriter(new FileWriter("src/main/video_metadata_with_SQL_query.csv"))) {
            writer.writeAll(list);
        }
    }
}
