package org.engcia.Utils;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.engcia.BC.KnowledgeBase;
import org.json.JSONObject;
import sun.net.www.http.HttpClient;

import java.util.Iterator;

public class JSON {



    public static String generateJSON(KnowledgeBase kb){

      String s = " { \"adequateMinGPUBenchmark\":" + kb.adequateMinGPUBenchmark + ",\"adequateMinCPUBenchmark\":" +kb.adequateMinCPUBenchmark + ",\"gpuManufacturerPreferred\":\"" + kb.gpuManufacturerPreferred +
              "\",\"cpuManufacturerPreferred\":\"" + kb.cpuManufacturerPreferred +
               "\",\"maxBudget\":" + kb.minRAMPreferred +
                ",\"cpuCooler_isFanless\":\"" + String.valueOf(kb.cpuCooler.isFanless) +
                "\",\"cpuCooler_isWaterCooled\":\"" + String.valueOf(kb.cpuCooler.isWaterCooled) +
                "\",\"minRAM\":" + kb.minRAMPreferred +
                ",\"minRAMSpeed\":" + kb.minRAMSpeed +
                ",\"minEnergyEfficiencyNeeded\":\"" + kb.minEnergyEfficiencyNeeded.toString() +
                "\",\"minSSD_capacity\":" +  kb.minStorage.capacity +
                ",\"minHDD_capacity\":" + kb.minScndStorage.capacity +
                ",\"caseSizePreferred\":\"" + kb.caseSizePreferred.toString() +
                "\",\"caseColorPreferred\":\"" + kb.caseColorPreferred.toString() + "\"}";

        return s;
    }

    public static JSONObject communicateWithProlog(String json){
        // @Deprecated HttpClient httpClient = new DefaultHttpClient();
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        JSONObject jsonObj = new JSONObject();
        try {
            HttpPost request = new HttpPost("http://localhost:5101/findBestPC");
            StringEntity params = new StringEntity(json);
            request.addHeader("content-type", "application/json");
            request.setEntity(params);
            HttpResponse response = httpClient.execute(request);
            HttpEntity entity = response.getEntity();
            String responseString = EntityUtils.toString(entity, "UTF-8");
            System.out.println(responseString);
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return jsonObj;
    }

    public static void createPC(JSONObject json){
        System.out.println(json);
        for(Iterator iterator = json.keySet().iterator(); iterator.hasNext();) {
            String key = (String) iterator.next();
            System.out.println(key);
            System.out.println(json.get(key));
        }

    }

}
