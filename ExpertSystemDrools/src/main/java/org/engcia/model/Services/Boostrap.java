package org.engcia.model.Services;

import org.engcia.model.Components.*;

import java.util.ArrayList;
import java.util.List;

public class Boostrap {

    private void loadDatabase() {
        loadGPUs();
        loadCPUs();
        loadCPUCoolers();
        loadMotherboards();
        loadRAMs();
        loadPowerSupplies();
        loadStorages();
        loadCases();
    }

    private void loadGPUs() {
        List<GPU> listGPUs = new ArrayList<GPU>();
        // Load GPUs...
    }

    private void loadCPUs() {
        List<CPU> listCPUs = new ArrayList<CPU>();
        // Load CPUs...
    }

    private void loadCPUCoolers() {
        List<CPUCooler> listCPUCoolers = new ArrayList<CPUCooler>();
        // Load CPU Coolers...
    }


    private void loadMotherboards() {
        List<Motherboard> listMotherboards = new ArrayList<Motherboard>();
        // Load Motherboards...
    }

    private void loadRAMs() {
        List<RAM> listRAMs = new ArrayList<RAM>();
        // Load RAMs...
    }

    private void loadPowerSupplies() {
        List<PowerSupply> listPowerSupplies = new ArrayList<PowerSupply>();
        // Load Power Supplies...
    }

    private void loadStorages() {
        List<Storage> listStorages = new ArrayList<Storage>();
        // Load Storages...
    }

    private void loadCases() {
        List<Case> listCases= new ArrayList<Case>();
        // Load Cases...
    }
}