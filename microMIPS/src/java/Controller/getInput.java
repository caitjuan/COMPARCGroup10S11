package Controller;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Model.*;
import java.util.*;

@WebServlet(name = "getInput", urlPatterns = {"/getInput"})
public class getInput extends HttpServlet {

    Code c;
    Errors e;
    GPRegs r;
    Memory m1;
    Memory m2;
    ArrayList<Code> code = new ArrayList<>();
    ArrayList<Errors> errors = new ArrayList<>();
    ArrayList<GPRegs> regs = new ArrayList<>();
    ArrayList<Memory> mem = new ArrayList<>();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String codeInput = request.getParameter("codeInput");
        String[] registers = {"R0", "R1", "R2", "R3", "R4", "R5", "R6", "R7", "R8", "R9", "R10", "R11", "R12", "R13", "R14", "R15", "R16", "R17", "R18", "R19", "R20", "R21", "R22", "R23", "R24", "R25", "R26", "R27", "R28", "R29", "R30", "R31"};

        ArrayList<String> err = new ArrayList<>();
        ArrayList<String> errReg = new ArrayList<>();
        ArrayList<String> errMem = new ArrayList<>();
        ArrayList<String> inst = new ArrayList<>();

        String temp;
        
        DataInserter in = new DataInserter();
        DataUpdater up = new DataUpdater();
        
        up.reset();
        
        //GET REGISTERS
        for (int i = 0; i < 32; i++) {
            temp = isNumeric(request.getParameter(registers[i]));

            if (temp.equals("Invalid Input")) {
                errReg.add(registers[i] + ": Invalid Input");
            }

            r = new GPRegs(temp);
            regs.add(r);
        }

        try {
            if (errReg.isEmpty()) {
                for (int i = 0; i < 32; i++) {
                    in.insertReg(regs.get(i).getReg(), i);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        request.setAttribute("errReg", errReg);
        request.setAttribute("regs", regs);

        //GET MEMORY
        String hex;

        for (int i = 0; i < 256; i++) {
            hex = Integer.toHexString(i);
            char zeroExtend[] = new char[4];
            int extend = 3 - hex.length();

            for (int zero = 0; zero < extend; zero++) {
                zeroExtend[zero] = '0';
            }

            char tempChar[] = new char[3 - extend];
            hex.getChars(0, 3 - extend, tempChar, 0);

            for (int zero = 2; zero >= extend; zero--) {
                zeroExtend[zero] = tempChar[zero - extend];
            }

            hex = String.valueOf(zeroExtend);
            hex = hex.substring(0, 3);

            String temp1 = request.getParameter(hex.toUpperCase() + "0");
            String temp2 = request.getParameter(hex.toUpperCase() + "8");

            temp1 = isNumeric(temp1);
            temp2 = isNumeric(temp2);

            m1 = new Memory(hex.toUpperCase() + "0", temp1);
            m2 = new Memory(hex.toUpperCase() + "8", temp2);

            if (temp1.equals("Invalid Input")) {
                errMem.add(hex.toUpperCase() + "0" + ": Invalid Input");
            }
            if (temp2.equals("Invalid Input")) {
                errMem.add(hex.toUpperCase() + "8" + ": Invalid Input");
            }

            mem.add(m1);
            mem.add(m2);
        }

        try {
            if (errMem.isEmpty()) {
                for (int i = 0; i < mem.size(); i++) {
                    in.insertMemory(mem.get(i).getAddress(), mem.get(i).getValue());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        request.setAttribute("mem", mem);
        request.setAttribute("errMem", errMem);

        //GET CODE
        for (String line : codeInput.split("\\n")) {
            inst.add(line.toUpperCase());
        }

        request.setAttribute("inst", inst);

        err = errorCheck(inst);
        String opcode;
        if (err.size() > 1) {
            for (int i = 0; i < err.size() - 1; i++) {
                e = new Errors(err.get(i));
                errors.add(e);
            }
            request.setAttribute("errors", errors);
        } else {
            for (int i = 0; i < inst.size(); i++) {
                if (!inst.get(i).startsWith(";")) {
                    opcode = convertOPCODE(inst.get(i), inst, i);
                    c = new Code(inst.get(i), Integer.toHexString(4096 + (i * 4)), opcode, BINtoHEX(opcode)); //initialize model
                    code.add(c);
                }
            }
            try {
                for (int i = 0; i < code.size(); i++) {
                    in.insertCode(code.get(i).getInstruction(), code.get(i).getOpcode(), code.get(i).getHex(), code.get(i).getAddress());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            request.setAttribute("code", code);
        }

        RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
        rd.forward(request, response);

        errors.clear();
        regs.clear();
        code.clear();
        mem.clear();
        inst.clear();
        errReg.clear();
        errMem.clear();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    @Override
    public String getServletInfo() {
        return "Getting input";
    }

    private ArrayList<String> errorCheck(ArrayList<String> code) {
        int size = code.size();         //get the size of array
        String splitIns[], splitReg1[];
        int ctr = 0;
        String branchName[] = new String[100];

        ArrayList<String> errors = new ArrayList<>();

        //Counting number of Branch Name found in the instruction lines
        for (int br = 0; br < size; br++) {
            String array = code.get(br).trim();    //the index of array
            String temp[];

            int alength = array.length();
            int k = 0, exit = 0;

            while (k < alength && exit == 0) {
                if ((array.charAt(k) == ':')) {
                    exit = 1;
                }
                k++;
            }

            if (exit == 1) {
                temp = array.split(": ", 2);
                branchName[ctr] = temp[0];
                ctr++;
            }
        }

        for (int i = 0; i < size; i++) {
            String array = code.get(i).trim();    //the index of array
            String oldStr;
            String temp[];

            //For Branch Name
            int alength = array.length();
            int k = 0, exit = 0;
            int comma = 0;

            while (k < alength && exit == 0) {
                if ((array.charAt(k) == ':')) {
                    exit = 1;
                }
                k++;
            }

            k = 0;
            while (k < alength) {
                if ((array.charAt(k) == ',')) {
                    comma++;
                }
                k++;
            }

            if (exit == 1) {
                temp = array.split(": ", 2);
                oldStr = temp[1];
            } else {
                oldStr = array;
            }

            if (oldStr.contains(";") && !oldStr.startsWith(";")) {
                oldStr = oldStr.substring(0, oldStr.indexOf(";") - 1);
                oldStr = oldStr.trim();
            }

            //Check if instruction is correct
            if (!(oldStr.startsWith("LD") || oldStr.startsWith("SD") || oldStr.startsWith("DADDIU") || oldStr.startsWith("XORI") || oldStr.startsWith("BLTZ") || oldStr.startsWith("DADDU") || oldStr.startsWith("SLT") || oldStr.startsWith("BC") || oldStr.startsWith("NOP") || oldStr.startsWith(";")) && !oldStr.isEmpty()) {
                errors.add("Line " + (i + 1) + ": Instruction does not exist");
            }

            if (oldStr.isEmpty()) {
                errors.add("No code given");
            }

            //One Comma
            if (oldStr.startsWith("LD") || oldStr.startsWith("SD") || oldStr.startsWith("BLTZ")) {
                if (comma != 1) {
                    errors.add("Line " + (i + 1) + ": Syntax Error of comma for " + oldStr);
                }
            }

            //Two Comma
            if (oldStr.startsWith("DADDIU") || oldStr.startsWith("XORI") || oldStr.startsWith("DADDU") || oldStr.startsWith("SLT")) {
                if (comma != 2) {
                    errors.add("Line " + (i + 1) + ": Syntax Error of comma for " + oldStr);
                }
            }

            //Zero Comma
            if (oldStr.startsWith("BC")) {
                if (comma != 0) {
                    errors.add("Line " + (i + 1) + ": Syntax Error of comma for " + oldStr);
                }
            }

            //For DADDIU rt, XORI rt, DADDU rd and SLT rd ONLY and
            //For DADDIU rs, XORI rs, DADDU rs and SLT rs ONLY and 
            //For LD rt and BLTZ rs ONLY and 
            //For SD rt ONLY
            if (oldStr.startsWith("DADDIU") || oldStr.startsWith("XORI") || oldStr.startsWith("DADDU") || oldStr.startsWith("SLT") || oldStr.startsWith("LD") || oldStr.startsWith("BLTZ") || oldStr.startsWith("SD")) {
                if (oldStr.startsWith("DADDIU")) {
                    splitIns = oldStr.split("DADDIU ", 2);
                } else if (oldStr.startsWith("XORI")) {
                    splitIns = oldStr.split("XORI ", 2);
                } else if (oldStr.startsWith("DADDU")) {
                    splitIns = oldStr.split("DADDU ", 2);
                } else if (oldStr.startsWith("SLT")) {
                    splitIns = oldStr.split("SLT ", 2);
                } else if (oldStr.startsWith("LD")) {
                    splitIns = oldStr.split("LD ", 2);                  //Split LD rt,offset(base) to [0] = "LD " and [1] = "rt,offset(base)"
                } else if (oldStr.startsWith("SD")) {
                    splitIns = oldStr.split("SD ", 2);                  //Split LD rt,offset(base) to [0] = "LD " and [1] = "rt,offset(base)"
                } else {
                    splitIns = oldStr.split("BLTZ ", 2);
                }

                if (oldStr.startsWith("LD") || oldStr.startsWith("BLTZ") || oldStr.startsWith("SD")) {
                    splitReg1 = splitIns[1].split(",", 2);
                } else {
                    splitReg1 = splitIns[1].split(", ", 3);
                }

                int length1, m = 0, result;
                int length2, result2;
                int exit2 = 1;
                length1 = splitReg1[0].length();
                length2 = splitReg1[1].length();

                boolean isValidReg = true;
                boolean isValidReg2 = true;
                if (splitReg1[0].startsWith("R") || splitReg1[0].startsWith("r")) {
                    /* Remove first("R") to get the INTEGER part */
                    try {
                        Integer.parseInt(splitReg1[0].substring(1));
                    } catch (NumberFormatException e) {
                        isValidReg = false;
                    }
                } else {
                    isValidReg = false;
                }
                if (oldStr.startsWith("DADDIU") || oldStr.startsWith("XORI") || oldStr.startsWith("DADDU") || oldStr.startsWith("SLT")) {
                    if (splitReg1[1].startsWith("R") || splitReg1[1].startsWith("r")) {
                        /* Remove first("R") to get the INTEGER part */
                        try {
                            Integer.parseInt(splitReg1[1].substring(1));
                        } catch (NumberFormatException e) {
                            isValidReg2 = false;
                        }
                    } else {
                        isValidReg2 = false;
                    }

                    StringBuilder sb2 = new StringBuilder(40);
                    String jString2;
                    char reg2[] = new char[length2];
                    splitReg1[1].getChars(0, length2, reg2, 0);
                    int j2 = 0;
                    exit2 = 0;

                    while (m < length2) {
                        if (reg2[m] >= '0' && reg2[m] <= '9') {
                            sb2.append(reg2[m]);
                        }
                        m++;
                    }

                    jString2 = sb2.toString();
                    result2 = Integer.parseInt(jString2);

                    while (j2 < 32 && exit2 == 0) {
                        if (result2 == j2) {
                            exit2 = 1;
                        }
                        j2++;
                    }
                }

                StringBuilder sb = new StringBuilder(40);
                String jString;
                char reg1[] = new char[length1];
                splitReg1[0].getChars(0, length1, reg1, 0);

                int j = 1;
                int exit1 = 0;

                m = 0;
                while (m < length1) {
                    if (reg1[m] >= '0' && reg1[m] <= '9') {
                        sb.append(reg1[m]);
                    }
                    m++;
                }

                jString = sb.toString();
                result = Integer.parseInt(jString);

                while (j < 32 && exit1 == 0) {
                    if (result == j) {
                        exit1 = 1;
                    }
                    j++;
                }

                if (exit1 == 0 || !isValidReg) {
                    errors.add("Line " + (i + 1) + ": Syntax Error reg1 for " + oldStr);
                } else if (exit2 == 0 || !isValidReg2) {
                    errors.add("Line " + (i + 1) + ": Syntax Error reg2 for " + oldStr);
                }
            }
            //For DADDU rt and SLT rt ONLY
            if (oldStr.startsWith("DADDU") || oldStr.startsWith("SLT")) {
                if (oldStr.startsWith("DADDU")) {
                    splitIns = oldStr.split("DADDU ", 2);
                } else {
                    splitIns = oldStr.split("SLT ", 2);
                }

                splitReg1 = splitIns[1].split(", ", 3); //[0]=rt, [1]=rs, [2]=imm

                int length1, m = 0, result;
                length1 = splitReg1[2].length();    //rs.length

                boolean isValidReg = true;
                if (splitReg1[2].startsWith("R") || splitReg1[2].startsWith("r")) {
                    /* Remove first("R") to get the INTEGER part */
                    try {
                        Integer.parseInt(splitReg1[2].substring(1));
                    } catch (NumberFormatException e) {
                        isValidReg = false;
                    }
                } else {
                    isValidReg = false;
                }

                StringBuilder sb = new StringBuilder(40);
                String jString;
                char reg1[] = new char[length1];
                int j = 0;
                int exit1 = 0;

                if (isValidReg) {
                    splitReg1[2].getChars(0, length1, reg1, 0);
                    while (m < length1) {
                        if (reg1[m] >= '0' && reg1[m] <= '9') {
                            sb.append(reg1[m]);
                        }
                        m++;
                    }
                    jString = sb.toString();
                    result = Integer.parseInt(jString);

                    while (j < 32 && exit1 == 0) {
                        if (result == j) {
                            exit1 = 1;
                        }
                        j++;
                    }
                }

                if (exit1 == 0 || !isValidReg) {
                    errors.add("Line " + (i + 1) + ": Syntax Error reg3 for (" + oldStr + ")");
                }
            }
            //For LD base and SD base ONLY
            if (oldStr.startsWith("LD") || oldStr.startsWith("SD")) {
                if (oldStr.startsWith("LD")) {
                    splitIns = oldStr.split("LD ", 2);
                } else {
                    splitIns = oldStr.split("SD ", 2);
                }

                splitReg1 = splitIns[1].split(", ", 2); //[0]=rt, [1]=offset(base)

                int length1, m = 0, result;
                length1 = splitReg1[1].length();    //rs.length

                boolean isValidReg = true;
                String a = splitReg1[1].substring(5, length1 - 1);

                if (a.startsWith("R") || a.startsWith("r")) {
                    /* Remove first("R") to get the INTEGER part */
                    try {
                        Integer.parseInt(a.substring(1));
                    } catch (NumberFormatException e) {
                        isValidReg = false;
                    }
                } else {
                    isValidReg = false;
                }

                StringBuilder sb = new StringBuilder(40);
                String jString;
                length1 = a.length();
                char reg1[] = new char[length1];
                a.getChars(0, length1, reg1, 0);

                int j = 0;
                int exit1 = 0;

                while (m < length1) {
                    if (reg1[m] >= '0' && reg1[m] <= '9') {
                        sb.append(reg1[m]);
                    }
                    m++;
                }
                jString = sb.toString();
                result = Integer.parseInt(jString);

                while (j < 32 && exit1 == 0) {
                    if (result == j) {
                        exit1 = 1;
                    }
                    j++;
                }

                if (exit1 == 0 || !isValidReg) {
                    errors.add("Line " + (i + 1) + ": Syntax Error reg3 for " + oldStr);
                }
            }
            //For DADDIU Imm and XORI Imm ONLY and 
            //For LD offset and SD offset ONLY
            if (oldStr.startsWith("DADDIU") || oldStr.startsWith("XORI") || oldStr.startsWith("LD") || oldStr.startsWith("SD")) {
                if (oldStr.startsWith("DADDIU")) {
                    splitIns = oldStr.split("DADDIU ", 2);
                } else if (oldStr.startsWith("XORI")) {
                    splitIns = oldStr.split("XORI ", 2);
                } else if (oldStr.startsWith("LD")) {
                    splitIns = oldStr.split("LD ", 2);
                } else {
                    splitIns = oldStr.split("SD ", 2);
                }

                if (oldStr.startsWith("LD") || oldStr.startsWith("SD")) {
                    splitReg1 = splitIns[1].split(", ", 2); //[0]=rt, [1]=offset(base)               
                } else {
                    splitReg1 = splitIns[1].split(", ", 3); //[0]=rt, [1]=rs, [2]=#FFFF
                }
                String a;
                if (oldStr.startsWith("DADDIU") || oldStr.startsWith("XORI")) {
                    if (!(splitReg1[2].startsWith("#"))) {
                        errors.add("Line " + (i + 1) + ": Syntax Error reg3 for " + oldStr);
                    }
                    a = splitReg1[2];
                } else {
                    a = splitReg1[1].substring(0, 4);
                }
            }
            //For BLTZ offset and BC offset ONLY
            if (oldStr.startsWith("BLTZ") || oldStr.startsWith("BC")) {
                String a;
                if (oldStr.startsWith("BLTZ")) {
                    splitIns = oldStr.split("BLTZ ", 2);
                    splitReg1 = splitIns[1].split(", ", 2); //[0]=rt, [1]=offset
                    a = splitReg1[1];
                } else {
                    splitIns = oldStr.split("BC ", 2); //[1] = offset
                    a = splitIns[1];
                }

                int exit1 = 0;
                int why = 0;
                while (why < ctr && exit1 == 0) {
                    if (branchName[why].equals(a)) {
                        exit1 = 1;
                    }
                    why++;
                }

                if (exit1 == 0) {
                    errors.add("Line " + (i + 1) + ": Syntax Error reg3 for " + oldStr);
                }
            }
        }

        errors.add(" ");

        return errors;
    }

    private static String removeSpace(String ins) {
        return ins.substring(1);
    }

    private static String HEXtoBIN(String binary) {
        char[] binaryARRAY = binary.toUpperCase().toCharArray();
        int size = binary.length();
        StringBuilder builder = new StringBuilder();
        String HEXtoBINTable[][] = {
            {"0000", "0"}, {"0001", "1"}, {"0010", "2"},
            {"0011", "3"}, {"0100", "4"}, {"0101", "5"},
            {"0110", "6"}, {"0111", "7"}, {"1000", "8"},
            {"1001", "9"}, {"1010", "A"}, {"1011", "B"},
            {"1100", "C"}, {"1101", "D"}, {"1110", "E"},
            {"1111", "F"},};

        for (int ctr = 0; ctr < size; ctr++) {
            for (int tctr = 0; tctr < 16; tctr++) {
                if (HEXtoBINTable[tctr][1].equals(String.valueOf(binaryARRAY[ctr]))) {
                    builder.append(HEXtoBINTable[tctr][0]);
                    break;
                }
            }
        }

        return builder.toString();
    }

    private static String BINtoHEX(String binary) {
        int digit = binary.length() / 4;
        StringBuilder builder = new StringBuilder();
        String BINtoHEXTable[][] = {
            {"0000", "0"}, {"0001", "1"}, {"0010", "2"},
            {"0011", "3"}, {"0100", "4"}, {"0101", "5"},
            {"0110", "6"}, {"0111", "7"}, {"1000", "8"},
            {"1001", "9"}, {"1010", "A"}, {"1011", "B"},
            {"1100", "C"}, {"1101", "D"}, {"1110", "E"},
            {"1111", "F"},};

        for (int ctr = 0; ctr < digit; ctr++) {
            for (int tctr = 0; tctr < 16; tctr++) {
                if (BINtoHEXTable[tctr][0].equals(binary.substring((ctr * 4), (ctr * 4 + 4)))) {
                    builder.append(BINtoHEXTable[tctr][1]);
                    break;
                }
            }
        }

        return builder.toString();
    }

    private static String DECtoBIN(int num, int bits) {
        StringBuilder builder = new StringBuilder();
        double bit = (bits - 1) * 1.0,
                value = num * 1.0;
        int doLoop;
        do {
            if (Math.pow(2.0, bit) <= value) {
                builder.append("1");
                value = value - Math.pow(2.0, bit);
            } else {
                builder.append("0");
            }
            bit = bit - 1.0;
            doLoop = Double.compare(bit, 0.0);
        } while (doLoop >= 0);

        return builder.toString();
    }

    private static String twosCOMP(int num, int bits) {
        double neg = Math.pow(2.0, bits) - num;
        return (DECtoBIN((int) neg, bits));
    }

    private static String findLabelOffset(String label, ArrayList<String> code, int pctr, int bits) {
        String offset = "";
        int result;

        for (int ctr = 0; ctr < code.size(); ctr++) {
            if (code.get(ctr).startsWith(label)) {
                if (ctr < pctr) {
                    result = pctr - ctr + 1;
                    offset = twosCOMP(result, bits);
                } else {
                    result = ctr - pctr - 1;
                    offset = DECtoBIN(result, bits);
                }
            }
        }

        return offset;
    }

    private static String convertFORMAT1(String instruction, String OPCODE, ArrayList<String> code, int pctr) {
        StringBuilder builder = new StringBuilder();
        /* FORMAT 1: Instruction offset
            opcode  offset
            OPCODE  splitIns[1]
         */
        String[] splitIns = instruction.split(" ", 2);
        while (splitIns[1].startsWith(" ") == true) {
            splitIns[1] = removeSpace(splitIns[1]);
        }

        splitIns[1] = splitIns[1].trim();

        builder.append(OPCODE);
        builder.append(findLabelOffset(splitIns[1], code, pctr, 26));

        return builder.toString();
    }

    private static String convertFORMAT2(String instruction, String OPCODE) {
        StringBuilder builder = new StringBuilder();
        /* FORMAT 2: Instruction RT, RS, immediate
            opcode  rs  	rt          imm
            OPCODE  splitRS[0]  splitRT[0]  splitRS[1]
         */
        String[] splitIns = instruction.split(" ", 2);
        while (splitIns[1].startsWith(" ") == true) {
            splitIns[1] = removeSpace(splitIns[1]);
        }
        String[] splitRT = splitIns[1].split(",", 2);
        while (splitRT[1].startsWith(" ") == true) {
            splitRT[1] = removeSpace(splitRT[1]);
        }
        String[] splitRS = splitRT[1].split(",", 2);
        while (splitRS[1].startsWith(" ") == true) {
            splitRS[1] = removeSpace(splitRS[1]);
        }

        splitRS[1] = splitRS[1].trim();

        builder.append(OPCODE);
        builder.append(DECtoBIN(Integer.parseInt(splitRS[0].substring(1)), 5));
        builder.append(DECtoBIN(Integer.parseInt(splitRT[0].substring(1)), 5));
        builder.append(HEXtoBIN(splitRS[1].substring(1)));

        return builder.toString();
    }

    private static String convertFORMAT3(String instruction, String OPCODE) {
        StringBuilder builder = new StringBuilder();

        /* FORMAT 3: Instruction RT, offset(base)
            opcode  base            rt/ft       offset 
            OPCODE  splitoffset[1]  splitRT[0]  splitoffset[0] */
        String[] splitIns = instruction.split(" ", 2);      // RESULT: [0] = "instruction"; [1] = "RT, offset(base)"
        while (splitIns[1].startsWith(" ") == true) {
            splitIns[1] = removeSpace(splitIns[1]);
        }
        String[] splitRT = splitIns[1].split(",", 2);       // RESULT: [0] = "RT"; [1] = "offset(base)"
        while (splitRT[1].startsWith(" ") == true) {
            splitRT[1] = removeSpace(splitRT[1]);
        }
        String[] splitoffset = splitRT[1].split("\\(", 2);  // RESULT: [0] = "offset; [1] = "base"

        splitoffset[1] = splitoffset[1].substring(0, splitoffset[1].indexOf(")"));

        builder.append(OPCODE);
        builder.append(DECtoBIN(Integer.parseInt(splitoffset[1].substring(1)), 5));
        builder.append(DECtoBIN(Integer.parseInt(splitRT[0].substring(1)), 5));
        builder.append(HEXtoBIN(splitoffset[0]));

        return builder.toString();
    }

    private static String convertFORMAT4(String instruction, String OPCODE, String RT, ArrayList<String> code, int pctr) {
        StringBuilder builder = new StringBuilder();

        /* FORMAT 4: Instruction RS, offset
            opcode  rs          rt  offset	
            OPCODE  splitRS[0]  RT  splitRS[1]  */
        String[] splitIns = instruction.split(" ", 2);
        while (splitIns[1].startsWith(" ") == true) {
            splitIns[1] = removeSpace(splitIns[1]);
        }
        String[] splitRS = splitIns[1].split(",", 2);
        while (splitRS[1].startsWith(" ") == true) {
            splitRS[1] = removeSpace(splitRS[1]);
        }

        splitRS[1] = splitRS[1].trim();

        builder.append(OPCODE);
        builder.append(DECtoBIN(Integer.parseInt(splitRS[0].substring(1)), 5));
        builder.append(RT);
        builder.append(findLabelOffset(splitRS[1], code, pctr, 16));

        return builder.toString();
    }

    private static String convertFORMAT5(String instruction, String OPCODE, String SA, String FUNC) {
        StringBuilder builder = new StringBuilder();

        /* FORMAT 5: Instruction RD, RS, RT
            opcode  rs          rt          rd          sa  func	
            OPCODE  splitRS[0]  splitRS[1]  splitRD[0]  SA  FUNC    */
        String[] splitIns = instruction.split(" ", 2);
        while (splitIns[1].startsWith(" ") == true) {
            splitIns[1] = removeSpace(splitIns[1]);
        }
        String[] splitRD = splitIns[1].split(",", 2);
        while (splitRD[1].startsWith(" ") == true) {
            splitRD[1] = removeSpace(splitRD[1]);
        }
        String[] splitRS = splitRD[1].split(",", 2);
        while (splitRS[1].startsWith(" ") == true) {
            splitRS[1] = removeSpace(splitRS[1]);
        }

        splitRS[1] = splitRS[1].trim();

        builder.append(OPCODE);
        builder.append(DECtoBIN(Integer.parseInt(splitRS[0].substring(1)), 5));
        builder.append(DECtoBIN(Integer.parseInt(splitRS[1].substring(1)), 5));
        builder.append(DECtoBIN(Integer.parseInt(splitRD[0].substring(1)), 5));
        builder.append(SA);
        builder.append(FUNC);

        return builder.toString();
    }

    private static String convertOPCODE(String instruction, ArrayList<String> code, int pctr) {
        String opcode;
        String opcodeTable[][] = {
            /* opcode   sa       func */
            {"110010", "", ""}, // FORMAT1 BC
            {"011001", "", ""}, // FORMAT2 DADDIU
            {"001110", "", ""}, // FORMAT2 XORI
            {"110111", "", ""}, // FORMAT3 LD
            {"111111", "", ""}, // FORMAT3 SD
            {"000001", "00000", ""}, // FORMAT4 BLTZ
            {"000000", "00000", "101101"}, //FORMAT5 DADDU
            {"000000", "00000", "101010"}, //FORMAT5 SLT
        };
        /*  FORMAT 1:   opcode  offset 
            FORMAT 2:   opcode  rs  	rt      imm
            FORMAT 3:   opcode  base  	rt/ft  	offset
            FORMAT 4:	opcode	rs      sat     offset
            FORMAT 5:	opcode	rs      rt      rd       sa   func */

        if (instruction.contains(";") && !instruction.startsWith(";")) {
            instruction = instruction.substring(0, instruction.indexOf(";"));
            instruction = instruction.trim();
        }

        if (instruction.startsWith("BC") || instruction.startsWith("bc")) {
            opcode = convertFORMAT1(instruction, opcodeTable[0][0], code, pctr);
        } else if (instruction.startsWith("DADDIU") || instruction.startsWith("daddiu")) {
            opcode = convertFORMAT2(instruction, opcodeTable[1][0]);
        } else if (instruction.startsWith("XORI") || instruction.startsWith("xori")) {
            opcode = convertFORMAT2(instruction, opcodeTable[2][0]);
        } else if (instruction.startsWith("LD") || instruction.startsWith("ld")) {
            opcode = convertFORMAT3(instruction, opcodeTable[3][0]);
        } else if (instruction.startsWith("SD") || instruction.startsWith("sd")) {
            opcode = convertFORMAT3(instruction, opcodeTable[4][0]);
        } else if (instruction.startsWith("BLTZ") || instruction.startsWith("bltz")) {
            opcode = convertFORMAT4(instruction, opcodeTable[5][0], opcodeTable[5][1], code, pctr);
        } else if (instruction.startsWith("DADDU") || instruction.startsWith("daddu")) {
            opcode = convertFORMAT5(instruction, opcodeTable[6][0], opcodeTable[6][1], opcodeTable[6][2]);
        } else if (instruction.startsWith("SLT") || instruction.startsWith("slt")) {
            opcode = convertFORMAT5(instruction, opcodeTable[7][0], opcodeTable[7][1], opcodeTable[7][2]);
        } else if (instruction.startsWith("NOP") || instruction.startsWith("nop")) {
            opcode = "00000000000000000000000000000000";
        } else {
            String[] splitLabel = instruction.split(" ", 2);
            while (splitLabel[1].startsWith(" ") == true) {
                splitLabel[1] = removeSpace(splitLabel[1]);
            }
            opcode = convertOPCODE(splitLabel[1], code, pctr);
        }

        return opcode;
    }

    private String isNumeric(String reg) {

        if (reg.length() != 16
                || (reg.charAt(0) != '-' && Character.digit(reg.charAt(0), 16) == -1)) {
            return "Invalid Input";
        }
        if (reg.length() == 1 && reg.charAt(0) == '-') {
            return "Invalid Input";
        }

        for (int i = 1; i < reg.length(); i++) {
            if (Character.digit(reg.charAt(i), 16) == -1) {
                return "Invalid Input";
            }
        }

        return reg;
    }
}
