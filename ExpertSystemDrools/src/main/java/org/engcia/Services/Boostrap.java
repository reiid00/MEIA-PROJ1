package org.engcia.Services;

import org.drools.compiler.compiler.io.File;
import org.drools.compiler.shade.org.eclipse.jdt.internal.compiler.parser.Scanner;
import org.engcia.model.Components.*;

import java.io.FileNotFoundException;
import java.util.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;



public class Boostrap {

    private void loadDatabase() throws IOException {
        loadGPUs();
        loadCPUs();
        loadCPUCoolers();
        loadMotherboards();
        loadRAMs();
        loadPowerSupplies();
        loadStorages();
        loadCases();
    }

    public void loadGPUs() throws IOException {
        List<GPU> listGPUs = new ArrayList<GPU>();

        BufferedReader bufReader = new BufferedReader(new FileReader("GPUs.txt"));
        String line = bufReader.readLine();
        line.split(";");
        while (line != null) {
            if (!line.contains("//") || line.trim().length() > 0) {
                GPU gpu = new GPU();
                gpu.id = "";
                gpu.manufacturer = "";
                gpu.name = "";
                gpu.basePrice = 0;
//                gpu.launchDate = "";
//                gpu.atxCompatibilityTypes = "";

                listGPUs.add(gpu);
            }
        }

        // Load GPUs...
    }

    private void loadCPUs() throws IOException {
        List<CPU> listCPUs = new ArrayList<CPU>();

        BufferedReader bufReader = new BufferedReader(new FileReader("CPUs.txt"));
        String line = bufReader.readLine();
        while (line != null) {
            if (!line.contains("//") || line.trim().length() > 0) {
                CPU cpu = new CPU();
                cpu.id = "";
                cpu.manufacturer = "";
                cpu.name = "";
                cpu.basePrice = 0;
//                cpu.launchDate = "";
                cpu.coreCount = 0;
                cpu.threadsCount = 0;
                cpu.boostClock = 0;
                cpu.voltage = 0;
                cpu.benchmarkScore = 0;
//                cpu.socket = "";

                listCPUs.add(cpu);
            }
        }
        // Load CPUs...
    }

    private void loadCPUCoolers() throws IOException {
        List<CPUCooler> listCPUCoolers = new ArrayList<CPUCooler>();
        // Load CPU Coolers...

        BufferedReader bufReader = new BufferedReader(new FileReader("CPUCoolers.txt"));
        String line = bufReader.readLine();
        while (line != null) {
            if (!line.contains("//") || line.trim().length() > 0) {
                CPUCooler cpuCooler = new CPUCooler();
                cpuCooler.id = "";
                cpuCooler.manufacturer = "";
                cpuCooler.name = "";
                cpuCooler.basePrice = 0;
//                cpuCooler.launchDate = "";
//                cpuCooler.isWaterCooled = 0;
//                cpuCooler.isFanless = 0;
//                cpuCooler.socketCompatabilityList = "";

                listCPUCoolers.add(cpuCooler);
            }
        }
    }


    private void loadMotherboards() throws IOException {
        List<Motherboard> listMotherboards = new ArrayList<Motherboard>();
        // Load Motherboards...

        BufferedReader bufReader = new BufferedReader(new FileReader("MotherBoards.txt"));
        String line = bufReader.readLine();
        while (line != null) {
            if (!line.contains("//") || line.trim().length() > 0) {
                Motherboard motherboard = new Motherboard();
                motherboard.id = "";
                motherboard.manufacturer = "";
                motherboard.name = "";
                motherboard.basePrice = 0;
//                motherboard.launchDate = 0;
//                motherboard.atxType = 0;
                motherboard.maxMemoryRam = 0;
//                motherboard.ramType = 0;
                motherboard.ramSlots = 0;
//                motherboard.ramSpeedList = 0;
//                motherboard.socketCompatabilityList = "";

                listMotherboards.add(motherboard);
            }
        }
    }

    private void loadRAMs() throws IOException {
        List<RAM> listRAMs = new ArrayList<RAM>();
        // Load RAMs...

        BufferedReader bufReader = new BufferedReader(new FileReader("RAMs.txt"));
        String line = bufReader.readLine();
        while (line != null) {
            if (!line.contains("//") || line.trim().length() > 0) {
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
        }
    }

    private void loadPowerSupplies() throws IOException {
        List<PowerSupply> listPowerSupplies = new ArrayList<PowerSupply>();
        // Load Power Supplies...

        BufferedReader bufReader = new BufferedReader(new FileReader("PowerSupplies.txt"));
        String line = bufReader.readLine();
        while (line != null) {
            if (!line.contains("//") || line.trim().length() > 0) {
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
        }
    }

    private void loadStorages() throws IOException {
        List<Storage> listStorages = new ArrayList<Storage>();
        // Load Storages...

        BufferedReader bufReader = new BufferedReader(new FileReader("Storage.txt"));
        String line = bufReader.readLine();
        while (line != null) {
            if (!line.contains("//") || line.trim().length() > 0) {
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
        }
    }

    private void loadCases() throws IOException {
        List<Case> listCases= new ArrayList<Case>();
        // Load Cases...
        BufferedReader bufReader = new BufferedReader(new FileReader("Cases.txt"));
        String line = bufReader.readLine();
        while (line != null) {
            if (!line.contains("//") || line.trim().length() > 0) {
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
        }
    }
}
