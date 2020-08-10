/**
 * Alipay.com Inc.
 * Copyright (c) 2004‐2020 All Rights Reserved.
 */
package com.mitrais.bootcamp;

import com.mitrais.bootcamp.domain.ATMSimulationException;
import com.mitrais.bootcamp.domain.CSVReaderUtilException;
import com.mitrais.bootcamp.repository.ATMRepository;
import org.apache.commons.cli.*;

/**
 * @author Aji Atin Mulyadi
 * @version $Id: Main.java, v 0.1 2020‐07‐06 10:17 Aji Atin Mulyadi Exp $$
 */
public class Main {

    public static Option getDirectoryOption(){
        return Option.builder("d").desc("Data source directory")
                .longOpt("dir")
                .type(String.class)
                .hasArg()
                .build();
    }

    public static void main(String[] args) {

        Options options = new Options();
        options.addOption(getDirectoryOption());

        try {
            CommandLineParser cmdParser = new DefaultParser();
            CommandLine cmd = cmdParser.parse(options, args);

            if(cmd.hasOption("d")){
                String path = cmd.getOptionValue("d");
                System.out.println("account data path : "+path);
                ATMRepository atmRepository = new ATMRepository();
                atmRepository.loadAccountDataFromCSV(path);
                ATMController.init(atmRepository).run();

            } else {
                HelpFormatter formatter = new HelpFormatter();
                formatter.printHelp("java -jar atm.jar -d c:\\account.csv", options);
            }
        }catch (ParseException e){
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("java -jar atm_.jar -d c:\\account.csv", options);
        } catch (ATMSimulationException e){
            System.out.println(e.getErrorContext().getErrorMessage());
        } catch (CSVReaderUtilException e){
            System.out.println(e.getErrorContext().getErrorMessage());
        } catch (Exception e){
            System.out.println(e.getMessage());
        }

    }

}