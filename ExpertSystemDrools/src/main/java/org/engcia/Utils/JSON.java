package org.engcia.Utils;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.engcia.BC.KnowledgeBase;
import org.engcia.BD.*;
import org.engcia.Main;
import org.json.JSONObject;

import java.util.Iterator;

public class JSON {



    public static String generateJSON(KnowledgeBase kb){

        int ram = 0;
        if (kb.minRAMPreferred > 0) ram = kb.minRAMPreferred;
                else ram = kb.minRAM;


      String s = " { \"adequateMinGPUBenchmark\":" + kb.adequateMinGPUBenchmark + ",\"adequateMinCPUBenchmark\":" +kb.adequateMinCPUBenchmark + ",\"gpuManufacturerPreferred\":\"" + kb.gpuManufacturerPreferred +
              "\",\"cpuManufacturerPreferred\":\"" + kb.cpuManufacturerPreferred +
               "\",\"maxBudget\":" + kb.maxBudget +
                ",\"cpuCooler_isFanless\":\"" + String.valueOf(kb.cpuCooler.isFanless) +
                "\",\"cpuCooler_isWaterCooled\":\"" + String.valueOf(kb.cpuCooler.isWaterCooled) +
                "\",\"minRAM\":" + ram +
                ",\"minRAMSpeed\":" + kb.minRAMSpeed +
                ",\"minEnergyEfficiencyNeeded\":\"" + kb.minEnergyEfficiencyNeeded.toString() +
                "\",\"minSSD_capacity\":" +  kb.minStorage.capacity +
                ",\"minHDD_capacity\":" + kb.minScndStorage.capacity +
                ",\"caseSizePreferred\":\"" + kb.caseSizePreferred.toString() +
                "\",\"caseColorPreferred\":\"" + kb.caseColorPreferred.toString() +
                "\",\"needsCPUCooler\":\"" + String.valueOf(kb.needsCPUCooler) +
                "\",\"needsDedicatedGPU\":\"" + String.valueOf(kb.needsDedicatedGPU) +
                "\",\"prefersDedicatedGPU\":\"" + String.valueOf(kb.prefersDedicatedGPU) + "\"}";

        return s;
    }

    public static JSONObject communicateWithProlog(String json){
        // @Deprecated HttpClient httpClient = new DefaultHttpClient();
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        JSONObject jsonObj = new JSONObject();
        try {
            HttpPost request = new HttpPost("http://localhost:5102/findBestPC");
            StringEntity params = new StringEntity(json);
            request.addHeader("content-type", "application/json");
            request.setEntity(params);
            HttpResponse response = httpClient.execute(request);
            HttpEntity entity = response.getEntity();
            String responseString = EntityUtils.toString(entity, "UTF-8");
            jsonObj = new JSONObject(responseString);
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return jsonObj;
    }

    public static void createPC(JSONObject json){
        PowerSupply ps = new PowerSupply();
        CPUCooler cpuCooler = new CPUCooler();
        Storage ssd = new Storage();
        Storage hdd = new Storage();
        Motherboard mb = new Motherboard();
        CPU cpu = new CPU();
        GPU gpu = new GPU();
        RAM ram = new RAM();
        Case caseC = new Case();
        for(Iterator iterator = json.keySet().iterator(); iterator.hasNext();) {
            String key = (String) iterator.next();
            if(json.get(key).equals("NA"))
                continue;
            switch(key.toUpperCase()) {
                case "POWERSUPPLY":
                    for (PowerSupply psu : Main.powerSupplies){
                        if (psu.id.equals(json.get(key))){
                            ps = psu;
                            break;
                        }
                    }
                    break;
                case "CPUCOOLER":
                    for (CPUCooler cpuC : Main.cpuCoolers){
                        if (cpuC.id.equals(json.get(key))){
                            cpuCooler = cpuC;
                            break;
                        }
                    }
                    break;
                case "SSD":
                    for (Storage stor : Main.storages){
                        if (stor.id.equals(json.get(key))){
                            ssd = stor;
                            break;
                        }
                    }
                    break;
                case "MOTHERBOARD":
                    for (Motherboard motherboard : Main.motherboards){
                        if (motherboard.id.equals(json.get(key))){
                            mb = motherboard;
                            break;
                        }
                    }
                    break;
                case "CPU":
                    for (CPU cpu1 : Main.cpus){
                        if (cpu1.id.equals(json.get(key))){
                            cpu = cpu1;
                            break;
                        }
                    }
                    break;
                case "HDD":
                    for (Storage stor : Main.storages){
                        if (stor.id.equals(json.get(key))){
                            hdd = stor;
                            break;
                        }
                    }
                    break;
                case "GPU":
                    for (GPU gpu1 : Main.gpus){
                        if (gpu1.id.equals(json.get(key))){
                            gpu = gpu1;
                            break;
                        }
                    }
                    break;
                case "RAM":
                    for (RAM ram1 : Main.rams){
                        if (ram1.id.equals(json.get(key))){
                            ram = ram1;
                            break;
                        }
                    }
                    break;
                case "CASE":
                    for (Case case1 : Main.cases){
                        if (case1.id.equals(json.get(key))){
                            caseC = case1;
                            break;
                        }
                    }
                    break;
                default:
                    // code block
            }
        }
        System.out.println("***********************************************************************************");
        int custoTotal = (int) (gpu.basePrice + cpu.basePrice + cpuCooler.basePrice + mb.basePrice + ssd.basePrice + hdd.basePrice + ps.basePrice + caseC.basePrice + ram.basePrice);
        System.out.println("Foi concluído o seguinte PC feito à medida com um custo total de " + custoTotal + "€ :");
        if (!gpu.name.isEmpty()) System.out.println("Placa Gráfica: " + gpu.brand + " " + gpu.manufacturer + " " + gpu.name + ", " + gpu.basePrice + "€ ");
        System.out.println("Processador: " +  cpu.manufacturer + " " + cpu.name + ", " + cpu.basePrice + "€ ");
        if (!cpuCooler.name.isEmpty()) System.out.println("Refrigerador de Processador: " +  cpuCooler.manufacturer + " " + cpuCooler.name + ", " + cpuCooler.basePrice + "€ ");
        System.out.println("Placa-mãe: " +  mb.manufacturer + " " + mb.name + ", " + mb.basePrice + "€ ");
        System.out.println("1º disco : " +  ssd.manufacturer + " " + ssd.name +  " " + ssd.capacity + " GB , " + ssd.basePrice + "€ ");
        if (!hdd.name.isEmpty()) System.out.println("2º disco : " +  hdd.manufacturer + " " + hdd.name +  " " + hdd.capacity + " GB , " + hdd.basePrice + "€ ");
        System.out.println("Memória RAM : " +  ram.manufacturer + " " + ram.name +  " " + ram.capacity + " GB , " + ram.basePrice + "€ ");
        System.out.println("Fonte de Alimentação : " +  ps.manufacturer + " " + ps.name +" , " + ps.basePrice + "€ ");
        System.out.println("Caixa : " +  caseC.manufacturer + " " + caseC.name +" , " + caseC.basePrice + "€ ");
        System.out.println("***********************************************************************************");

    }

}
