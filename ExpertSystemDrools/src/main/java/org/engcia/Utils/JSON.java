package org.engcia.Utils;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.engcia.BC.KnowledgeBase;
import org.json.JSONObject;
import sun.net.www.http.HttpClient;

public class JSON {



    public static JSONObject generateJSON(KnowledgeBase kb){
        JSONObject json = new JSONObject();
        json.put("adequateMinGPUBenchmark",kb.adequateMinGPUBenchmark);
        json.put("adequateMinCPUBenchmark",kb.adequateMinCPUBenchmark);
        json.put("gpuManufacturerPreferred",kb.gpuManufacturerPreferred);
        json.put("cpuManufacturerPreferred",kb.cpuManufacturerPreferred);
        json.put("maxBudget",kb.maxBudget);
        json.put("cpuCooler_isFanless",String.valueOf(kb.cpuCooler.isFanless));
        json.put("cpuCooler_isWaterCooled",String.valueOf(kb.cpuCooler.isWaterCooled));
        if(kb.minRAMPreferred > 0) json.put("minRAM",kb.minRAMPreferred);
        else json.put("minRAM",kb.minRAM);
        json.put("minRAMSpeed",kb.minRAMSpeed);
        json.put("minEnergyEfficiencyNeeded",kb.minEnergyEfficiencyNeeded.toString());
        json.put("minSSD_capacity",kb.minStorage.capacity);
        json.put("minHDD_capacity",kb.minScndStorage.capacity);
        json.put("caseSizePreferred",kb.caseSizePreferred);
        json.put("caseColorPreferred",kb.caseColorPreferred);
        System.out.println(json.toString());
        return json;
    }

    public static JSONObject communicateWithProlog(JSONObject json){
        // @Deprecated HttpClient httpClient = new DefaultHttpClient();
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        JSONObject jsonObj = new JSONObject();
        try {
            HttpPost request = new HttpPost("http://localhost:5102/findBestPC");
            StringEntity params = new StringEntity(json.toString());
            request.addHeader("content-type", "application/json");
            request.setEntity(params);
            HttpResponse response = httpClient.execute(request);
            jsonObj = (JSONObject) response;
        } catch (Exception ex) {
        }
        return jsonObj;
    }

}
