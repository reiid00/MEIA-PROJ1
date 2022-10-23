package org.engcia.Services;

import org.engcia.model.Components.*;

import java.util.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;



public class Boostrap {

    public static void loadDatabase() throws IOException {
        //loadGPUs();
        loadCPUs();
        loadCPUCoolers();
        loadMotherboards();
        /*loadRAMs();
        loadPowerSupplies();
        loadStorages();
        loadCases();*/
    }

    public static void loadGPUs() throws IOException {
        List<GPU> listGPUs = new ArrayList<>();

        BufferedReader bufReader = new BufferedReader(new FileReader("BD/GPUs.txt"));
        String line = bufReader.readLine();
        while (line != null) {
            if (!(line.contains("--") || line.trim().isEmpty())) {
                List<String> lineArray = Arrays.asList(line.split(";"));
                GPU gpu = new GPU();
                gpu.id = lineArray.get(0);
                gpu.manufacturer = lineArray.get(1);
                gpu.name = lineArray.get(2);
                gpu.basePrice = Float.parseFloat(lineArray.get(3));
                String[] date = lineArray.get(4).split("/");
                gpu.launchDate = new Date(Integer.parseInt(date[2]),Integer.parseInt(date[1]),Integer.parseInt(date[0]));
                gpu.brand = GPU.GPUBrand.valueOf(lineArray.get(5));
                gpu.memory = Integer.parseInt(lineArray.get(6));
                gpu.memoryType = GPU.GPUMemoryType.valueOf(lineArray.get(7));
                gpu.maxClock = Integer.parseInt(lineArray.get(8));
                gpu.voltage = Integer.parseInt(lineArray.get(9));
                gpu.fansCount = Integer.parseInt(lineArray.get(10));
                List<String> atxListString = Arrays.asList(lineArray.get(11).split("\\[")[1].split("]")[0].split(","));
                for(String atx : atxListString)
                {
                    gpu.atxCompatibilityTypes.add(ATXCompatibilityType.valueOf(atx));
                }
                gpu.benchmarkScore = Integer.parseInt(lineArray.get(12));
                System.out.println(gpu);
                listGPUs.add(gpu);
            }
            line = bufReader.readLine();
        }

        // Load GPUs...
    }

    private static void loadCPUs() throws IOException {
        List<CPU> listCPUs = new ArrayList<>();

        BufferedReader bufReader = new BufferedReader(new FileReader("BD/CPUs.txt"));
        String line = bufReader.readLine();
        while (line != null) {
            if (!(line.contains("--") || line.trim().isEmpty())) {
                List<String> lineArray = Arrays.asList(line.split(";"));
                CPU cpu = new CPU();
                cpu.id = lineArray.get(0);
                cpu.manufacturer = lineArray.get(1);
                cpu.name = lineArray.get(2);
                cpu.basePrice = Integer.parseInt(lineArray.get(3));
                String[] date = lineArray.get(4).split("/");
                cpu.launchDate = new Date(Integer.parseInt(date[2]),Integer.parseInt(date[1]),Integer.parseInt(date[0]));
                cpu.coreCount = Integer.parseInt(lineArray.get(5));
                cpu.threadsCount = Integer.parseInt(lineArray.get(6));
                cpu.boostClock = Float.parseFloat(lineArray.get(7));
                cpu.voltage = Integer.parseInt(lineArray.get(8));
                cpu.benchmarkScore = Integer.parseInt(lineArray.get(9));
                cpu.socket = SocketType.valueOf(lineArray.get(10));

                System.out.println(cpu);
                listCPUs.add(cpu);
            }
            line = bufReader.readLine();
        }
        // Load CPUs...
    }

    private static void loadCPUCoolers() throws IOException {
        List<CPUCooler> listCPUCoolers = new ArrayList<CPUCooler>();
        // Load CPU Coolers...

        BufferedReader bufReader = new BufferedReader(new FileReader("BD/CPUCoolers.txt"));
        String line = bufReader.readLine();
        while (line != null) {
            if (!(line.contains("--") || line.trim().isEmpty())) {
                List<String> lineArray = Arrays.asList(line.split(";"));
                CPUCooler cpuCooler = new CPUCooler();
                cpuCooler.id = lineArray.get(0);
                cpuCooler.manufacturer = lineArray.get(1);
                cpuCooler.name = lineArray.get(2);
                cpuCooler.basePrice = Integer.parseInt(lineArray.get(3));
                String[] date = lineArray.get(4).split("/");
                cpuCooler.launchDate = new Date(Integer.parseInt(date[2]),Integer.parseInt(date[1]),Integer.parseInt(date[0]));
                cpuCooler.isWaterCooled = Boolean.getBoolean(lineArray.get(5));
                cpuCooler.isFanless = Boolean.getBoolean(lineArray.get(6));
                List<String> socketList = Arrays.asList(lineArray.get(7).split("\\[")[1].split("]")[0].split(","));
                for(String socket : socketList)
                {
                    cpuCooler.socketCompatabilityList.add(SocketType.valueOf(socket));
                }
                listCPUCoolers.add(cpuCooler);
            }
            line = bufReader.readLine();
        }
    }


    private static void loadMotherboards() throws IOException {
        List<Motherboard> listMotherboards = new ArrayList<Motherboard>();
        // Load Motherboards...

        BufferedReader bufReader = new BufferedReader(new FileReader("BD/MotherBoards.txt"));
        String line = bufReader.readLine();
        while (line != null) {
            if (!(line.contains("--") || line.trim().isEmpty())) {
                List<String> lineArray = Arrays.asList(line.split(";"));
                Motherboard motherboard = new Motherboard();
                motherboard.id = lineArray.get(0);
                motherboard.manufacturer = lineArray.get(1);
                motherboard.name = lineArray.get(2);
                motherboard.basePrice = Float.parseFloat(lineArray.get(3));
                String[] date = lineArray.get(4).split("/");
                motherboard.launchDate = new Date(Integer.parseInt(date[2]),Integer.parseInt(date[1]),Integer.parseInt(date[0]));
                List<String> socketList = Arrays.asList(lineArray.get(5).split("\\[")[1].split("]")[0].split(","));
                for(String socket : socketList)
                {
                    motherboard.socketCompatabilityList.add(SocketType.valueOf(socket));
                }

                motherboard.atxType = ATXCompatibilityType.valueOf(lineArray.get(6));
                motherboard.maxMemoryRam = Integer.parseInt(lineArray.get(7));
                motherboard.ramType = RAMType.valueOf(lineArray.get(8));
                motherboard.ramSlots = Integer.parseInt(lineArray.get(9));
                motherboard.ramSpeedList =Arrays.asList(lineArray.get(10).split("\\[")[1].split("]")[0].split(","));;

                System.out.println(motherboard);
                listMotherboards.add(motherboard);
            }
            line = bufReader.readLine();
        }
    }

    private void loadRAMs() throws IOException {
        List<RAM> listRAMs = new ArrayList<RAM>();
        // Load RAMs...

        BufferedReader bufReader = new BufferedReader(new FileReader("RAMs.txt"));
        String line = bufReader.readLine();
        while (line != null) {
            if (!(line.contains("--") || line.trim().isEmpty())) {
                RAM ram = new RAM();
                ram.id = "";
                ram.manufacturer = "";
                ram.name = "";
                ram.basePrice = 0;
//                ram.launchDate = 0;
                ram.speed = 0;
                ram.capacity = 0;
                ram.slotsCount = 0;
//                ram.ramType = 0;
                ram.voltage = 0;

                listRAMs.add(ram);
            }
            line = bufReader.readLine();
        }
    }

    private void loadPowerSupplies() throws IOException {
        List<PowerSupply> listPowerSupplies = new ArrayList<PowerSupply>();
        // Load Power Supplies...

        BufferedReader bufReader = new BufferedReader(new FileReader("PowerSupplies.txt"));
        String line = bufReader.readLine();
        while (line != null) {
            if (!(line.contains("--") || line.trim().isEmpty())) {
                PowerSupply powerSupply = new PowerSupply();
                powerSupply.id = "";
                powerSupply.manufacturer = "";
                powerSupply.name = "";
                powerSupply.basePrice = 0;
//                powerSupply.launchDate = 0;
                powerSupply.capacity = 0;
//                powerSupply.modular = 0;
//                powerSupply.atxCompatibilityTypes = 0;
//                powerSupply.memoryType = 0;

                listPowerSupplies.add(powerSupply);
            }
            line = bufReader.readLine();
        }
    }

    private void loadStorages() throws IOException {
        List<Storage> listStorages = new ArrayList<Storage>();
        // Load Storages...

        BufferedReader bufReader = new BufferedReader(new FileReader("Storage.txt"));
        String line = bufReader.readLine();
        while (line != null) {
            if (!(line.contains("--") || line.trim().isEmpty())) {
                Storage storage = new Storage();
                storage.id = "";
                storage.manufacturer = "";
                storage.name = "";
                storage.basePrice = 0;
//                storage.launchDate = 0;
//                storage.isSSD = 0;
                storage.cacheGB = 0;
                storage.capacityGB = 0;
                storage.benchmark = 0;

                listStorages.add(storage);
            }
            line = bufReader.readLine();
        }
    }

    private void loadCases() throws IOException {
        List<Case> listCases= new ArrayList<Case>();
        // Load Cases...
        BufferedReader bufReader = new BufferedReader(new FileReader("Cases.txt"));
        String line = bufReader.readLine();
        while (line != null) {
            if (!(line.contains("--") || line.trim().isEmpty())) {
                Case cases = new Case();
                cases.id = "";
                cases.manufacturer = "";
                cases.name = "";
                cases.basePrice = 0;
//                cases.launchDate = 0;
//                cases.sizeType = 0;
//                cases.atxCompatibilityList = 0;
                cases.color = "";

                listCases.add(cases);
            }
            line = bufReader.readLine();
        }
    }
}
