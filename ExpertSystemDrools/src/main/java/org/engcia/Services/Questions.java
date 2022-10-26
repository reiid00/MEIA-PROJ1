package org.engcia.Services;

import org.engcia.Components.Storage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Questions {


    public static List<Storage> questionStorage(Storage storage) throws IOException {

        List<Storage> storageList = new ArrayList<>();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("O sistema sugeriu o disco ideal, pretende modificá-lo/adicionar um novo?");
        System.out.println("Disco Ideal " + storage);
        System.out.println();
        System.out.println("1. Modificar / Adicionar um novo");
        System.out.println("2. manter");
        int choice = Integer.parseInt(br.readLine());
        if (choice == 1)
        {
            System.out.println("Pretende modificar o disco ou adicionar um novo?");
            System.out.println("1. Modificar");
            System.out.println("2. Adicionar um novo");
            choice = Integer.parseInt(br.readLine());
            if(choice == 1)
            {
                System.out.println("Indique o tipo de Disco que pretende");
                System.out.println("1. SSD");
                System.out.println("2. HDD");
                int typeChoice = Integer.parseInt(br.readLine());
                if (typeChoice == 1) storage.isSSD = true;
                else storage.isSSD = false;
                System.out.println("Indique o tamanho pretendido (GB)");
                int sizeChoice = Integer.parseInt(br.readLine());
                storage.capacityGB = String.valueOf(sizeChoice);
                storageList.add(storage);
                return storageList;
            } else if (choice == 2) {
                storageList.add(storage);
                Storage s2 = new Storage();
                s2.isSSD = true;
                if (storage.isSSD) s2.isSSD = false;
                if(s2.isSSD) System.out.println("Indique o tamanho pretendido (GB) para o SSD");
                else System.out.println("Indique o tamanho pretendido (GB) para o HDD");
                s2.capacityGB = br.readLine();
                storageList.add(s2);
                return storageList;
            }

        }
        storageList.add(storage);
        return storageList;
    }
}
