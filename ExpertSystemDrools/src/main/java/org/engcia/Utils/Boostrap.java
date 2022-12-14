package org.engcia.Utils;

import org.engcia.BD.*;
import org.engcia.Main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;



public class Boostrap {

    public static void loadBD() throws IOException {
        loadGPUs();
        loadCPUs();
        loadCPUCoolers();
        loadMotherboards();
        loadRAMs();
        loadPowerSupplies();
        loadStorages();
        loadCases();
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
                if(!lineArray.get(4).isEmpty()){
                    String[] date = lineArray.get(4).split("/");
                    gpu.launchDate = new Date(Integer.parseInt(date[2]),Integer.parseInt(date[1]),Integer.parseInt(date[0]));
                }
                gpu.brand = GPU.GPUBrand.valueOf(lineArray.get(5).toUpperCase());
                gpu.memory = Integer.parseInt(lineArray.get(6));
                gpu.memoryType = GPU.GPUMemoryType.valueOf(lineArray.get(7).toUpperCase());
                gpu.maxClock = Integer.parseInt(lineArray.get(8));
                gpu.voltage = Integer.parseInt(lineArray.get(9));
                gpu.fansCount = Integer.parseInt(lineArray.get(10));
                List<String> atxListString = Arrays.asList(lineArray.get(11).split("\\[")[1].split("]")[0].split(","));
                for(String atx : atxListString)
                {
                    gpu.atxCompatibilityTypes.add(ATXCompatibilityType.valueOf(atx.toUpperCase()));
                }
                gpu.benchmarkScore = Integer.parseInt(lineArray.get(12));
                listGPUs.add(gpu);
            }
            line = bufReader.readLine();
        }
        Main.gpus = listGPUs;
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
                if(!lineArray.get(4).isEmpty()){
                    String[] date = lineArray.get(4).split("/");
                    cpu.launchDate = new Date(Integer.parseInt(date[2]),Integer.parseInt(date[1]),Integer.parseInt(date[0]));
                }
                cpu.coreCount = Integer.parseInt(lineArray.get(5));
                cpu.threadsCount = Integer.parseInt(lineArray.get(6));
                cpu.boostClock = Float.parseFloat(lineArray.get(7));
                cpu.voltage = Integer.parseInt(lineArray.get(8));
                cpu.benchmarkScore = Integer.parseInt(lineArray.get(9));
                cpu.socket = SocketType.valueOf(lineArray.get(10).toUpperCase());

                listCPUs.add(cpu);
            }
            line = bufReader.readLine();
        }
        Main.cpus = listCPUs;
    }

    private static void loadCPUCoolers() throws IOException {
        List<CPUCooler> listCPUCoolers = new ArrayList<>();

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
                if(!lineArray.get(4).isEmpty()){
                    String[] date = lineArray.get(4).split("/");
                    cpuCooler.launchDate = new Date(Integer.parseInt(date[2]),Integer.parseInt(date[1]),Integer.parseInt(date[0]));
                }
                cpuCooler.voltage = Integer.parseInt(lineArray.get(5));
                cpuCooler.isWaterCooled = Boolean.getBoolean(lineArray.get(6));
                cpuCooler.isFanless = Boolean.getBoolean(lineArray.get(7));
                List<String> socketList = Arrays.asList(lineArray.get(8).split("\\[")[1].split("]")[0].split(","));
                for(String socket : socketList)
                {
                    cpuCooler.socketCompatabilityList.add(SocketType.valueOf(socket.toUpperCase()));
                }
                listCPUCoolers.add(cpuCooler);
            }
            line = bufReader.readLine();
        }
        Main.cpuCoolers = listCPUCoolers;
    }


    private static void loadMotherboards() throws IOException {
        List<Motherboard> listMotherboards = new ArrayList<>();

        BufferedReader bufReader = new BufferedReader(new FileReader("BD/Motherboards.txt"));
        String line = bufReader.readLine();
        while (line != null) {
            if (!(line.contains("--") || line.trim().isEmpty())) {
                List<String> lineArray = Arrays.asList(line.split(";"));
                Motherboard motherboard = new Motherboard();
                motherboard.id = lineArray.get(0);
                motherboard.manufacturer = lineArray.get(1);
                motherboard.name = lineArray.get(2);
                motherboard.basePrice = Float.parseFloat(lineArray.get(3));
                if(!lineArray.get(4).isEmpty()){
                    String[] date = lineArray.get(4).split("/");
                    motherboard.launchDate = new Date(Integer.parseInt(date[2]),Integer.parseInt(date[1]),Integer.parseInt(date[0]));
                }
                List<String> socketList = Arrays.asList(lineArray.get(5).split("\\[")[1].split("]")[0].split(","));
                for(String socket : socketList)
                {
                    motherboard.socketCompatabilityList.add(SocketType.valueOf(socket.toUpperCase()));
                }

                motherboard.atxType = ATXCompatibilityType.valueOf(lineArray.get(6).toUpperCase());
                motherboard.maxMemoryRam = Integer.parseInt(lineArray.get(7));
                motherboard.ramType = RAMType.valueOf(lineArray.get(8).toUpperCase());
                motherboard.ramSlots = Integer.parseInt(lineArray.get(9));
                motherboard.ramSpeedList =Arrays.asList(lineArray.get(10).split("\\[")[1].split("]")[0].split(","));;

                listMotherboards.add(motherboard);
            }
            line = bufReader.readLine();
        }
        Main.motherboards = listMotherboards;
    }

    private static void loadRAMs() throws IOException {
        List<RAM> listRAMs = new ArrayList<>();

        BufferedReader bufReader = new BufferedReader(new FileReader("BD/RAMs.txt"));
        String line = bufReader.readLine();
        while (line != null) {
            if (!(line.contains("--") || line.trim().isEmpty())) {
                List<String> lineArray = Arrays.asList(line.split(";"));
                RAM ram = new RAM();
                ram.id = lineArray.get(0);
                ram.manufacturer = lineArray.get(1);
                ram.name = lineArray.get(2);
                ram.basePrice = Float.parseFloat(lineArray.get(3));
                if(!lineArray.get(4).isEmpty()){
                    String[] date = lineArray.get(4).split("/");
                    ram.launchDate = new Date(Integer.parseInt(date[2]),Integer.parseInt(date[1]),Integer.parseInt(date[0]));
                }
                ram.speed = Integer.parseInt(lineArray.get(5));
                ram.capacity = Integer.parseInt(lineArray.get(6));
                ram.slotsCount =Integer.parseInt(lineArray.get(7));
                ram.ramType = RAMType.valueOf(lineArray.get(8).toUpperCase());
                ram.voltage = Float.parseFloat(lineArray.get(9));

                listRAMs.add(ram);
            }
            line = bufReader.readLine();
        }
        Main.rams = listRAMs;
    }

    private static void loadPowerSupplies() throws IOException {
        List<PowerSupply> listPowerSupplies = new ArrayList<>();

        BufferedReader bufReader = new BufferedReader(new FileReader("BD/PowerSupplies.txt"));
        String line = bufReader.readLine();
        while (line != null) {
            if (!(line.contains("--") || line.trim().isEmpty())) {
                List<String> lineArray = Arrays.asList(line.split(";"));
                PowerSupply powerSupply = new PowerSupply();
                powerSupply.id = lineArray.get(0);
                powerSupply.manufacturer = lineArray.get(1);
                powerSupply.name = lineArray.get(2);
                powerSupply.basePrice = Float.parseFloat(lineArray.get(3));
                if(!lineArray.get(4).isEmpty()){
                    String[] date = lineArray.get(4).split("/");
                    powerSupply.launchDate = new Date(Integer.parseInt(date[2]),Integer.parseInt(date[1]),Integer.parseInt(date[0]));
                }
                powerSupply.capacity = Integer.parseInt(lineArray.get(5));
                powerSupply.memoryType = PowerSupply.EnergyEfficiency.valueOf(lineArray.get(6).toUpperCase());
                powerSupply.modular = PowerSupply.ModularType.valueOf(lineArray.get(7));
                List<String> atxListString = Arrays.asList(lineArray.get(8).split("\\[")[1].split("]")[0].split(","));
                for(String atx : atxListString)
                {
                    powerSupply.atxCompatibilityTypes.add(ATXCompatibilityType.valueOf(atx.toUpperCase()));
                }

                listPowerSupplies.add(powerSupply);
            }
            line = bufReader.readLine();
        }
        Main.powerSupplies = listPowerSupplies;
    }

    private static void loadStorages() throws IOException {
        List<Storage> listStorages = new ArrayList<>();

        BufferedReader bufReader = new BufferedReader(new FileReader("BD/Storages.txt"));
        String line = bufReader.readLine();
        while (line != null) {
            if (!(line.contains("--") || line.trim().isEmpty())) {
                List<String> lineArray = Arrays.asList(line.split(";"));
                Storage storage = new Storage();
                storage.id = lineArray.get(0);
                storage.manufacturer = lineArray.get(1);
                storage.name = lineArray.get(2);
                storage.basePrice = Float.parseFloat(lineArray.get(3));
                if(!lineArray.get(4).isEmpty()){
                    String[] date = lineArray.get(4).split("/");
                    storage.launchDate = new Date(Integer.parseInt(date[2]),Integer.parseInt(date[1]),Integer.parseInt(date[0]));
                }
                storage.isSSD = Boolean.getBoolean(lineArray.get(5));
                storage.isSATA = Boolean.getBoolean(lineArray.get(6));
                storage.capacity = Integer.parseInt(lineArray.get(7));
                storage.cache = Integer.parseInt(lineArray.get(8));
                storage.benchmarkScore = Integer.parseInt(lineArray.get(9));

                listStorages.add(storage);
            }
            line = bufReader.readLine();
        }
        Main.storages = listStorages;
    }

    private static void loadCases() throws IOException {
        List<Case> listCases= new ArrayList<>();

        BufferedReader bufReader = new BufferedReader(new FileReader("BD/Cases.txt"));
        String line = bufReader.readLine();
        while (line != null) {
            if (!(line.contains("--") || line.trim().isEmpty())) {
                List<String> lineArray = Arrays.asList(line.split(";"));
                Case cases = new Case();
                cases.id = lineArray.get(0);
                cases.manufacturer = lineArray.get(1);
                cases.name = lineArray.get(2);
                cases.basePrice = Float.parseFloat(lineArray.get(3));
                if(!lineArray.get(4).isEmpty()){
                    String[] date = lineArray.get(4).split("/");
                    cases.launchDate = new Date(Integer.parseInt(date[2]),Integer.parseInt(date[1]),Integer.parseInt(date[0]));
                }
                cases.sizeType = Case.TowerSizeType.valueOf(lineArray.get(5));
                List<String> atxListString = Arrays.asList(lineArray.get(6).split("\\[")[1].split("]")[0].split(","));
                for(String atx : atxListString)
                {
                    cases.atxCompatibilityList.add(ATXCompatibilityType.valueOf(atx.toUpperCase()));
                }
                cases.color = lineArray.get(7);

                listCases.add(cases);
            }
            line = bufReader.readLine();
        }
        Main.cases = listCases;
    }
}
