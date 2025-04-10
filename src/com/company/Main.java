package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    private class Adat {

        public String nev;
        public String varos;
        public String honap;
        public String ido;

        public Adat(String sor) {
            String[] s = sor.split(";");
            nev = s[0];
            varos = s[1];
            honap =s[2];
            ido = s[3];

        }
    }

    ArrayList<Adat> adat = new ArrayList<Adat>();

    private void betolt(String fajlnev) {
        Scanner be = null;
        try {
            be = new Scanner(new File(fajlnev),"UTF-8");
            while(be.hasNextLine()) adat.add(new Adat(be.nextLine()));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if(be!=null) be.close();
        }

    }

    public Main(){
        betolt("utazok.csv");
        System.out.printf("0)\t%d utazó adat beolvasva\n", adat.size());
        ArrayList<String> varos = new ArrayList<>(); for(Adat x : adat) if(!varos.contains(x.varos)) varos.add(x.varos);
        System.out.printf("1)\tÖsszesen %d városba utaztak\n", varos.size());
        int rand = (int)(Math.random() * adat.size()); Adat ran = adat.get(rand);
        System.out.printf("\tKözülük egy véletlenszerűen kiválasztott: %s\n", ran.varos);
        int u = 0; for(Adat x : adat) if(x.varos.equals(ran.varos)) u++;
        System.out.printf("\tEbbe a városba %d utazó érkezett\n", u);
        Adat legk = adat.get(0);
        int ora = Integer.parseInt(legk.ido.split(":")[0]);
        int perc = Integer.parseInt(legk.ido.split(":")[1]);
        for(Adat x : adat) if(Integer.parseInt(x.ido.split(":")[0]) < ora  && Integer.parseInt(x.ido.split(":")[1]) < perc ) legk = x;
        System.out.printf("2)\tLegkorábbi indulás: %s\n", legk.ido);
        int db = 0; for(Adat x : adat) if(Integer.parseInt(x.ido.split(":")[0]) < 12) db++;
        System.out.printf("\tÖsszesen %d utazás kezdődött délelőtt\n", db);

        ArrayList<String> nevek = new ArrayList<>();   ArrayList<String> van = new ArrayList<>();
        for(Adat x : adat) if(!nevek.contains(x.nev.split(" ")[0])){ nevek.add(x.nev.split(" ")[0]);} else { if(!van.contains(x.nev.split(" ")[0])) van.add(x.nev.split(" ")[0]);}
        System.out.printf("4)\tTöbbször szereplő vezetkéknevek: \n"); System.out.printf("\t");
        for(String x : van) System.out.printf("%s ", x); System.out.println();

        Adat ua = adat.get(0);
        System.out.printf("5)\tUgyanazon a napon kettőnél több utazás: ");
        ArrayList<String> na = new ArrayList<>();
        for(Adat x : adat) if(!na.contains(x.honap)) na.add(x.honap);
        for(String c : na){int t = 0; for(Adat x : adat) if(x.honap.equals(c)) t++; if(t>2) System.out.printf("%s (%d) ", c, t);}

        System.out.println();
        System.out.printf("6)\tSzegedre utazók kiírva a szeged.txt fájlba\n");
        PrintWriter ki = null;
        try {
            ki=new PrintWriter("szeged.txt","utf-8");
            for(Adat x : adat) if(x.varos.equals("Szeged")) ki.printf("%s (%s %s)\r\n", x.nev, x.honap, x.ido);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (ki != null) ki.close();
        }

    }

    public static void main(String[] args) {
	    new Main();
    }
}
