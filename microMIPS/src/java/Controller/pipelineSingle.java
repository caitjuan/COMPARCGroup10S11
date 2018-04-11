package Controller;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.ResultSet;
import java.util.*;
import Model.*;

@WebServlet(name = "pipelineSingle", urlPatterns = {"/pipelineSingle"})
public class pipelineSingle extends HttpServlet {

    private String IF_IR;
    private String IF_NPC;
    private String IF_PC;

    private String ID_IR;
    private String ID_NPC;
    private String ID_A;
    private String ID_B;
    private String ID_IMM;

    private String EX_IR;
    private String EX_B;
    private String EX_ALU;
    private String EX_COND;

    private String MEM_IR;
    private String MEM_ALU;
    private String MEM_RANGE;
    private String MEM_LMD;

    private String WB_RN;

    private boolean mayBranch;
    private boolean done;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String pcVal = request.getParameter("pcVal");
        int cyclenum = Integer.parseInt(request.getParameter("cyclenum"));
        DataExtractor ex = new DataExtractor();

        String instructionIF = null,
                opcodeIF = null,
                hexIF = null,
                addressIF = null,
                instructionEX = null,
                prevIF_IR = null,
                prevID_IR = null,
                prevID_NPC = null,
                prevID_A = null,
                prevID_B = null,
                prevID_IMM = null,
                prevEX_IR = null,
                prevEX_ALU = null,
                prevMEM_IR = null,
                prevMEM_ALU = null,
                instructionMEM = null,
                instructionWB = null,
                prevIF_NPC = null;

        try (PrintWriter out = response.getWriter()) {
            ResultSet prevCycle = ex.getCycle(cyclenum);
            ResultSet codeIF = ex.getCode(pcVal);

            try {
                prevCycle.beforeFirst();
                while (prevCycle.next()) {
                    prevIF_IR = prevCycle.getString(2);
                    prevIF_NPC = prevCycle.getString(3);
                    prevID_IR = prevCycle.getString(5);
                    prevID_NPC = prevCycle.getString(6);
                    prevID_A = prevCycle.getString(7);
                    prevID_B = prevCycle.getString(8);
                    prevID_IMM = prevCycle.getString(9);
                    prevEX_IR = prevCycle.getString(10);
                    prevEX_ALU = prevCycle.getString(12);
                    prevMEM_IR = prevCycle.getString(14);
                    prevMEM_ALU = prevCycle.getString(15);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                codeIF.beforeFirst();
                while (codeIF.next()) {
                    instructionIF = codeIF.getString(2);
                    opcodeIF = codeIF.getString(3);
                    hexIF = codeIF.getString(4);
                    addressIF = codeIF.getString(5);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            //IF
            IF_IR = hexIF;

            //ID
            if (prevIF_IR == null) {
                ID_IR = null;
                ID_NPC = null;
                ID_A = null;
                ID_B = null;
                ID_IMM = null;
            } else {
                ID_IR = prevIF_IR;
                ID_NPC = prevIF_NPC;
                ID_A = zeroExtend(BINtoHEX(HEXtoBIN(prevIF_IR).substring(11, 16)));
                ID_B = zeroExtend(BINtoHEX(HEXtoBIN(prevIF_IR).substring(6, 11)));
                ID_IMM = signExtend(BINtoHEX(HEXtoBIN(prevIF_IR).substring(16, 32)));
            }

            ResultSet codeEX = ex.getCodeWithHex(prevID_IR);

            try {
                codeEX.beforeFirst();
                while (codeEX.next()) {
                    instructionEX = codeEX.getString(2);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            //EX
            if (prevID_IR == null) {
                EX_IR = null;
                EX_ALU = null;
                EX_B = null;
                EX_COND = null;
            } else if (instructionEX.contains("DADDIU") || instructionEX.contains("XORI") || instructionEX.contains("DADDU") || instructionEX.contains("SLT")) {
                EX_IR = prevID_IR;
                if (instructionEX.contains("DADDIU")) {
                    EX_ALU = ADD(prevID_A, prevID_IMM);
                } else if (instructionEX.contains("XORI")) {
                    EX_ALU = XORI(prevID_A, prevID_IMM);
                } else if (instructionEX.contains("DADDU"){
                    EX_ALU = ADD(prevID_A, prevID_B);
                } else {
                    EX_ALU = SLT(prevID_A, prevID_B);
                }
                EX_B = prevID_B;
                EX_COND = "0";
            } else if (instructionEX.contains("LD") || instructionEX.contains("SD")) {
                EX_IR = prevID_IR;
                EX_ALU = ADD(prevID_A, prevID_IMM);
                EX_B = prevID_B;
                EX_COND = "0";
            } else if (instructionEX.contains("BC") || instructionEX.contains("BLTZ")) {
                EX_IR = prevID_IR;
                EX_ALU = ADD(prevID_NPC, signExtend(shiftLEFT(prevID_IMM)));
                EX_B = prevID_B;
                if (instructionEX.contains("BC")) {
                    EX_COND = "1";
                } else {
                    ResultSet register = ex.getReg(Integer.parseInt(prevID_A, 2));
                    String val = "0000000000000000";
                    try {
                        register.beforeFirst();
                        val = register.getString(2);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (Integer.parseInt(val, 16) < 0) {
                        EX_COND = "1";
                    } else {
                        EX_COND = "0";
                    }
                }
            }

            //cont IF
            if ((instructionEX.contains("BC") || instructionEX.contains("BLTZ")) && EX_COND == "1") {
                IF_PC = EX_ALU;
                IF_NPC = EX_ALU;
            } else {
                IF_PC = Integer.toHexString(Integer.parseInt(pcVal, 16) + 4);
                IF_NPC = IF_PC;
            }

            ResultSet codeMEM = ex.getCodeWithHex(prevEX_IR);

            try {
                codeMEM.beforeFirst();
                while (codeMEM.next()) {
                    instructionMEM = codeMEM.getString(2);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            //MEM
            if (prevEX_IR == null) {
                MEM_IR = null;
                MEM_ALU = null;
                MEM_RANGE = null;
                MEM_LMD = null;
            } else if (instructionMEM.contains("LD")) {
                MEM_IR = prevEX_IR;
                MEM_ALU = "N/A";
                //MEM_LMD = 
                MEM_RANGE = "N/A";
            } else if (instructionMEM.contains("SD")) {
                MEM_IR = prevEX_IR;
                MEM_ALU = "N/A";
                MEM_LMD = "N/A";
                //MEM_RANGE = "N/A";
            } else {
                MEM_IR = prevEX_IR;
                MEM_ALU = prevEX_ALU;
                MEM_LMD = "N/A";
                MEM_RANGE = "N/A";
            }

            ResultSet codeWB = ex.getCodeWithHex(prevMEM_IR);

            try {
                codeWB.beforeFirst();
                while (codeWB.next()) {
                    instructionMEM = codeWB.getString(2);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            //WB
            if (prevMEM_IR == null) {
                WB_RN = null;
            } else if (instructionMEM.contains("DADDU") || instructionMEM.contains("SLT")) {
                WB_RN = prevMEM_ALU;
                //reg
            } else if (instructionMEM.contains("DADDIU") || instructionMEM.contains("XORI")) {
                WB_RN = prevMEM_ALU;
                //reg
            } else if (instructionMEM.contains("LD")) {
                WB_RN = prevMEM_ALU;
                //reg
            } else {
                WB_RN = "N/A";
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private String zeroExtend(String value) {
        //Zero extend hex
        char zeroExtend[] = new char[16];
        int extend = 16 - value.length();
        for (int zero = 0; zero < extend; zero++) {
            zeroExtend[zero] = '0';
        }
        char tempChar[] = new char[16 - extend];
        value.getChars(0, 16 - extend, tempChar, 0);

        for (int zero = 15; zero >= extend; zero--) {
            zeroExtend[zero] = tempChar[zero - extend];
        }

        value = String.valueOf(zeroExtend);

        return value;
    }

    private String signExtend(String value) {
        //Sign extend hex
        char signExtend[] = new char[16];
        int extend = 16 - value.length();
        for (int sign = 0; sign < extend; sign++) {
            signExtend[sign] = value.charAt(0);
        }
        char tempChar[] = new char[16 - extend];
        value.getChars(0, 16 - extend, tempChar, 0);

        for (int sign = 15; sign >= extend; sign--) {
            signExtend[sign] = tempChar[sign - extend];
        }

        value = String.valueOf(signExtend);

        return value;
    }

    private String ADD(String a, String immB) {  //For Both DADDIU and DADDU na ito
        Integer temp1 = Integer.parseInt(a, 16);
        Integer temp2 = Integer.parseInt(immB, 16);

        Integer resultTemp = temp1 + temp2;

        String value;
        value = Integer.toHexString(resultTemp);

        return value.toUpperCase();
    }

    private String XORI(String a, String imm) {  //For Both DADDIU and DADDU na ito
        Integer temp1 = Integer.parseInt(a, 16);
        Integer temp2 = Integer.parseInt(imm, 16);

        Integer resultTemp = temp1 ^ temp2;

        String value;
        value = Integer.toHexString(resultTemp);

        return value.toUpperCase();
    }

    private String SLT(String RS, String RT) {
        Integer rs = Integer.parseInt(RS, 16);
        Integer rt = Integer.parseInt(RT, 16);

        if (rs < rt) {
            return "0000000000000001";
        } else {
            return "0000000000000000";
        }
    }

    private String shiftLEFT(String RS) {
        Integer rs = Integer.parseInt(RS, 16);
        RS = Integer.toBinaryString(rs);
        RS = RS + "00";
        rs = Integer.parseInt(RS, 2);
        RS = Integer.toHexString(rs);

        return RS.toUpperCase();
    }

    private String HEXtoBIN(String binary) {
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

    private String BINtoHEX(String binary) {
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

// <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
