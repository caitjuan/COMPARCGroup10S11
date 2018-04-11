<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="Model.*"%>
<%@page import="java.util.*"%>
<%@page import="javax.servlet.http.HttpServletRequest"%>
<%@page import="javax.servlet.http.HttpServletResponse"%>
<%@page import="javax.servlet.http.HttpServlet"%>

<!DOCTYPE html>

<html>
    <head>
        <title>microMIPS by The Potatoes</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
        <link rel="stylesheet" href="style.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
        <script>
            var registers = ["R0", "R1", "R2", "R3", "R4", "R5", "R6", "R7", "R8", "R9", "R10", "R11", "R12", "R13", "R14", "R15", "R16", "R17", "R18", "R19", "R20", "R21", "R22", "R23", "R24", "R25", "R26", "R27", "R28", "R29", "R30", "R31"];
            var GP = ["GP0", "GP1", "GP2", "GP3", "GP4", "GP5", "GP6", "GP7", "GP8", "GP9", "GP10", "GP11", "GP12", "GP13", "GP14", "GP15", "GP16", "GP17", "GP18", "GP19", "GP20", "GP21", "GP22", "GP23", "GP24", "GP25", "GP26", "GP27", "GP28", "GP29", "GP30", "GP31"];
            function customReset()
            {
                for (var i = 0; i < 32; i++) {
                    document.getElementById(registers[i]).value = "0000000000000000";
                }

                for (var i = 0; i < 32; i++) {
                    document.getElementById(GP[i]).value = "0000000000000000";
                }

                for (var i = 0; i < 256; i += 2) {
                    document.getElementById(i).value = "0000000000000000";
                    document.getElementById(i + 1).value = "0000000000000000";
                }

                for (var i = 0; i < 256; i += 2) {
                    document.getElementById("M" + i).value = "0000000000000000";
                    document.getElementById("M" + (i + 1)).value = "0000000000000000";
                }

                document.getElementById("GP0").value = "0000000000000000";
                document.getElementById("error").value = "";
            }
        </script>
    </head>
    <body>
        <div>
            <ul class="nav nav-tabs">
                <li><a data-toggle="tab" href="#home">Home</a></li>
                <li class="active"><a data-toggle="tab" href="#input">Input</a></li>
                <li><a data-toggle="tab" href="#view">View</a></li>
                <li><a data-toggle="tab" href="#run">Run</a></li>
            </ul>

            <div class="tab-content">

                <!-- HOME TAB -->
                <div id="home" class="tab-pane">
                    <h3>HOME</h3>
                    <br>
                    <h4>Welcome to microMIPS - the mini MIPSers</h4>
                    <br>
                    <p>Made by The Potatoes</p>
                    <p>Jaira Rose Bat-og = the Kawaii Potato</p>
                    <p>Caitlienne Diane Juan = the Derptato</p>
                    <p>Kimberly Wan = the Bad Potato</p>
                    <br>
                    <p>To guide you through this simulator, we've prepared this for you:</p>
                    <p>Input - You can put your code here. You can also edit the register and memory values.</p>
                    <p>View - The opcode will be displayed here. The registers and memory values are also displayed. Note that you can not edit this.</p>
                    <p>Run - This is where your code will run.</p>
                </div>

                <!-- INPUT TAB -->
                <div id="input" class="tab-pane in active">
                    <h3>INPUT</h3>
                    <form name="input-form" method="post" action="getInput">
                        <table class="table">
                            <thead>
                                <tr>
                                    <th>Code</th>
                                    <th>Registers</th>
                                    <th>Memory</th>
                                </tr>
                            </thead>
                            <tbody>
                                <tr>
                                    <th><textarea id="codeInput" name="codeInput" rows="15"><%
                                        ArrayList<String> inst = (ArrayList<String>) request.getAttribute("inst");
                                        if (inst != null) {
                                            for (int i = 0; i < inst.size(); i++) {
                                                out.print(inst.get(i));
                                            }
                                        }
                                            %></textarea></th>
                                    <th>
                                        <div style="overflow-y:scroll;height:305px;display:block;">
                                            <table>
                                                <%
                                                    ArrayList<GPRegs> regs = (ArrayList<GPRegs>) request.getAttribute("regs");
                                                    String[] regValue = new String[32];
                                                    if (regs == null) {
                                                        for (int i = 0; i < 32; i++) {
                                                            regValue[i] = "0000000000000000";
                                                        }
                                                    } else {
                                                        for (int i = 0; i < 32; i++) {
                                                            regValue[i] = regs.get(i).getReg();
                                                        }
                                                    }
                                                %>
                                                <tr><th>R0:</th> <th><input type="text" id="R0" name="R0" value="0000000000000000" readonly></th></tr>
                                                <tr><th>R1:</th> <th><input type="text" id="R1" name="R1" value="<%= regValue[1]%>"></th></tr>
                                                <tr><th>R2:</th> <th><input type="text" id="R2" name="R2" value="<%= regValue[2]%>"></th></tr>
                                                <tr><th>R3:</th> <th><input type="text" id="R3" name="R3" value="<%= regValue[3]%>"></th></tr>
                                                <tr><th>R4:</th> <th><input type="text" id="R4" name="R4" value="<%= regValue[4]%>"></th></tr>
                                                <tr><th>R5:</th> <th><input type="text" id="R5" name="R5" value="<%= regValue[5]%>"></th></tr>
                                                <tr><th>R6:</th> <th><input type="text" id="R6" name="R6" value="<%= regValue[6]%>"></th></tr>
                                                <tr><th>R7:</th> <th><input type="text" id="R7" name="R7" value="<%= regValue[7]%>"></th></tr>
                                                <tr><th>R8:</th> <th><input type="text" id="R8" name="R8" value="<%= regValue[8]%>"></th></tr>
                                                <tr><th>R9:</th> <th><input type="text" id="R9" name="R9" value="<%= regValue[9]%>"></th></tr>
                                                <tr><th>R10:</th> <th><input type="text" id="R10"  name="R10" value="<%= regValue[10]%>"></th></tr>
                                                <tr><th>R11:</th> <th><input type="text" id="R11"  name="R11" value="<%= regValue[11]%>"></th></tr>
                                                <tr><th>R12:</th> <th><input type="text" id="R12"  name="R12" value="<%= regValue[12]%>"></th></tr>
                                                <tr><th>R13:</th> <th><input type="text" id="R13"  name="R13" value="<%= regValue[13]%>"></th></tr>
                                                <tr><th>R14:</th> <th><input type="text" id="R14" name="R14" value="<%= regValue[14]%>"></th></tr>
                                                <tr><th>R15:</th> <th><input type="text" id="R15" name="R15" value="<%= regValue[15]%>"></th></tr>
                                                <tr><th>R16:</th> <th><input type="text" id="R16" name="R16" value="<%= regValue[16]%>"></th></tr>
                                                <tr><th>R17:</th> <th><input type="text" id="R17" name="R17" value="<%= regValue[17]%>"></th></tr>
                                                <tr><th>R18:</th> <th><input type="text" id="R18" name="R18" value="<%= regValue[18]%>"></th></tr>
                                                <tr><th>R19:</th> <th><input type="text" id="R19" name="R19" value="<%= regValue[19]%>"></th></tr>
                                                <tr><th>R20:</th> <th><input type="text" id="R20" name="R20" value="<%= regValue[20]%>"></th></tr>
                                                <tr><th>R21:</th> <th><input type="text" id="R21" name="R21" value="<%= regValue[21]%>"></th></tr>
                                                <tr><th>R22:</th> <th><input type="text" id="R22" name="R22" value="<%= regValue[22]%>"></th></tr>
                                                <tr><th>R23:</th> <th><input type="text" id="R23" name="R23" value="<%= regValue[23]%>"></th></tr>
                                                <tr><th>R24:</th> <th><input type="text" id="R24" name="R24" value="<%= regValue[24]%>"></th></tr>
                                                <tr><th>R25:</th> <th><input type="text" id="R25" name="R25" value="<%= regValue[25]%>"></th></tr>
                                                <tr><th>R26:</th> <th><input type="text" id="R26" name="R26" value="<%= regValue[26]%>"></th></tr>
                                                <tr><th>R27:</th> <th><input type="text" id="R27" name="R27" value="<%= regValue[27]%>"></th></tr>
                                                <tr><th>R28:</th> <th><input type="text" id="R28" name="R28" value="<%= regValue[28]%>"></th></tr>
                                                <tr><th>R29:</th> <th><input type="text" id="R29" name="R29" value="<%= regValue[29]%>"></th></tr>
                                                <tr><th>R30:</th> <th><input type="text" id="R30" name="R30" value="<%= regValue[30]%>"></th></tr>
                                                <tr><th>R31:</th> <th><input type="text" id="R31" name="R31" value="<%= regValue[31]%>"></th></tr>
                                            </table>
                                        </div>
                                    </th>
                                    <th>
                                        <div style="overflow-y:scroll;height:305px;display:block;">
                                            <%
                                                String hex;
                                                String value1 = "0000000000000000";
                                                String value2 = "0000000000000000";
                                                int j = 0;

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

                                                    ArrayList<Memory> mem = (ArrayList<Memory>) request.getAttribute("mem");
                                                    if (mem != null) {
                                                        value1 = mem.get(j).getValue();
                                                        value2 = mem.get(j + 1).getValue();
                                                    }


                                            %>
                                            <%= hex.toUpperCase()%>0: <input type="text" value="<%=value1%>" id="<%= j%>" name="<%= hex.toUpperCase()%>0" /><br>
                                            <%= hex.toUpperCase()%>8: <input type="text" value="<%=value2%>" id="<%= j + 1%>" name="<%= hex.toUpperCase()%>8" /><br>                        
                                            <%j += 2;
                                                }%>
                                        </div></th>
                                </tr>
                            </tbody>
                        </table>

                        <div class="center-div">
                            <input type="submit" value="Load" class="btn" />
                            <input type="button" value="Reset" class="btn" onclick="customReset();"/>
                        </div>
                    </form>

                    <table class="table">
                        <tbody>
                            <tr>
                                <th><textarea id="error" rows="4"><%
                                    ArrayList<String> errReg = (ArrayList<String>) request.getAttribute("errReg");
                                    if (errReg != null) {
                                        for (int i = 0; i < errReg.size(); i++) {
                                            out.println(errReg.get(i));
                                        }
                                    }

                                    ArrayList<String> errMem = (ArrayList<String>) request.getAttribute("errMem");
                                    if (errMem != null) {
                                        for (int i = 0; i < errMem.size(); i++) {
                                            out.println(errMem.get(i));
                                        }
                                    }

                                    ArrayList<Errors> e = (ArrayList<Errors>) request.getAttribute("errors");
                                    if (e != null) {
                                        for (int i = 0; i < e.size(); i++) {
                                            out.print(e.get(i).getError());
                                        }
                                    }%></textarea></th>
                            </tr>
                        </tbody>
                    </table>
                </div>

                <!-- VIEW TAB -->
                <div id="view" class="tab-pane">
                    <h3>VIEW</h3>

                    <table class="table">
                        <thead>
                            <tr>
                                <th>Opcode</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr>
                                <th>
                                    <table class="table">
                                        <thead>
                                            <tr>
                                                <th>Instruction</th>
                                                <th>R31-26</th>
                                                <th>R25-21</th>
                                                <th>R20-16</th>
                                                <th>R15-0</th>
                                                <th>Hex</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <tr>
                                                <%
                                                    ArrayList<Code> c = (ArrayList<Code>) request.getAttribute("code");
                                                    String instruction, reg1, reg2, reg3, reg4, h;
                                                    if (c != null) {
                                                        for (int i = 0; i < c.size(); i++) {
                                                            instruction = c.get(i).getInstruction();
                                                            reg1 = c.get(i).getOpcode().substring(0, 6);
                                                            reg2 = c.get(i).getOpcode().substring(6, 11);
                                                            reg3 = c.get(i).getOpcode().substring(11, 16);
                                                            reg4 = c.get(i).getOpcode().substring(16, 32);
                                                            h = c.get(i).getHex();
                                                %>
                                                <td><%= instruction%></td>
                                                <td><%= reg1%></td>
                                                <td><%= reg2%></td>
                                                <td><%= reg3%></td>
                                                <td><%= reg4%></td>
                                                <td><%= h%></td></tr>
                                                <%      }
                                                    }%>
                                        </tbody>
                                    </table>
                                </th>
                            </tr>
                        </tbody>
                        <tfoot>
                            <tr>
                                <td>&nbsp;</td>
                            </tr>
                        </tfoot>
                    </table>

                    <table class="table">
                        <thead>
                            <tr>
                                <th>Registers</th>
                                <th>Memory</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr>
                                <th>
                                    <div style="overflow-y:scroll;height:305px;display:block;">
                                        <table>
                                            <%  if (regs == null) {
                                                    for (int i = 0; i < 32; i++) {
                                                        regValue[i] = "0000000000000000";
                                                    }
                                                } else {
                                                    for (int i = 0; i < 32; i++) {
                                                        regValue[i] = regs.get(i).getReg();
                                                    }
                                                }
                                            %>
                                            <tr><th>R0:</th> <th><input type="text" name="R0" id="GP0" value="0000000000000000" readonly></th></tr>
                                            <tr><th>R1:</th> <th><input type="text" name="R1" id="GP1" value="<%= regValue[1]%>" readonly></th></tr>
                                            <tr><th>R2:</th> <th><input type="text" name="R2" id="GP2" value="<%= regValue[2]%>" readonly></th></tr>
                                            <tr><th>R3:</th> <th><input type="text" name="R3" id="GP3" value="<%= regValue[3]%>" readonly></th></tr>
                                            <tr><th>R4:</th> <th><input type="text" name="R4" id="GP4" value="<%= regValue[4]%>" readonly></th></tr>
                                            <tr><th>R5:</th> <th><input type="text" name="R5" id="GP5" value="<%= regValue[5]%>" readonly></th></tr>
                                            <tr><th>R6:</th> <th><input type="text" name="R6" id="GP6" value="<%= regValue[6]%>" readonly></th></tr>
                                            <tr><th>R7:</th> <th><input type="text" name="R7" id="GP7" value="<%= regValue[7]%>" readonly></th></tr>
                                            <tr><th>R8:</th> <th><input type="text" name="R8" id="GP8" value="<%= regValue[8]%>" readonly></th></tr>
                                            <tr><th>R9:</th> <th><input type="text" name="R9" id="GP9" value="<%= regValue[9]%>" readonly></th></tr>
                                            <tr><th>R10:</th> <th><input type="text" name="R10" id="GP10" value="<%= regValue[10]%>" readonly></th></tr>
                                            <tr><th>R11:</th> <th><input type="text" name="R11" id="GP11" value="<%= regValue[11]%>" readonly></th></tr>
                                            <tr><th>R12:</th> <th><input type="text" name="R12" id="GP12" value="<%= regValue[12]%>" readonly></th></tr>
                                            <tr><th>R13:</th> <th><input type="text" name="R13" id="GP13" value="<%= regValue[13]%>" readonly></th></tr>
                                            <tr><th>R14:</th> <th><input type="text" name="R14" id="GP14" value="<%= regValue[14]%>" readonly></th></tr>
                                            <tr><th>R15:</th> <th><input type="text" name="R15" id="GP15" value="<%= regValue[15]%>" readonly></th></tr>
                                            <tr><th>R16:</th> <th><input type="text" name="R16" id="GP16" value="<%= regValue[16]%>" readonly></th></tr>
                                            <tr><th>R17:</th> <th><input type="text" name="R17" id="GP17" value="<%= regValue[17]%>" readonly></th></tr>
                                            <tr><th>R18:</th> <th><input type="text" name="R18" id="GP18" value="<%= regValue[18]%>" readonly></th></tr>
                                            <tr><th>R19:</th> <th><input type="text" name="R19" id="GP19" value="<%= regValue[19]%>" readonly></th></tr>
                                            <tr><th>R20:</th> <th><input type="text" name="R20" id="GP20" value="<%= regValue[20]%>" readonly></th></tr>
                                            <tr><th>R21:</th> <th><input type="text" name="R21" id="GP21" value="<%= regValue[21]%>" readonly></th></tr>
                                            <tr><th>R22:</th> <th><input type="text" name="R22" id="GP22" value="<%= regValue[22]%>" readonly></th></tr>
                                            <tr><th>R23:</th> <th><input type="text" name="R23" id="GP23" value="<%= regValue[23]%>" readonly></th></tr>
                                            <tr><th>R24:</th> <th><input type="text" name="R24" id="GP24" value="<%= regValue[24]%>" readonly></th></tr>
                                            <tr><th>R25:</th> <th><input type="text" name="R25" id="GP25" value="<%= regValue[25]%>" readonly></th></tr>
                                            <tr><th>R26:</th> <th><input type="text" name="R26" id="GP26" value="<%= regValue[26]%>" readonly></th></tr>
                                            <tr><th>R27:</th> <th><input type="text" name="R27" id="GP27" value="<%= regValue[27]%>" readonly></th></tr>
                                            <tr><th>R28:</th> <th><input type="text" name="R28" id="GP28" value="<%= regValue[28]%>" readonly></th></tr>
                                            <tr><th>R29:</th> <th><input type="text" name="R29" id="GP29" value="<%= regValue[29]%>" readonly></th></tr>
                                            <tr><th>R30:</th> <th><input type="text" name="R30" id="GP30" value="<%= regValue[30]%>" readonly></th></tr>
                                            <tr><th>R31:</th> <th><input type="text" name="R31" id="GP31" value="<%= regValue[31]%>" readonly></th></tr>
                                        </table>
                                    </div>
                                </th>
                                <th>
                                    <div style="overflow-y:scroll;height:305px;display:block;">
                                        <%
                                            j = 0;
                                            int k = 0;
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

                                                ArrayList<Memory> mem = (ArrayList<Memory>) request.getAttribute("mem");
                                                if (mem != null) {
                                                    value1 = mem.get(j).getValue();
                                                    value2 = mem.get(j + 1).getValue();
                                                }

                                                j += 2;
                                        %>
                                        <%= hex.toUpperCase()%>0: <input type="text" id="M<%= k%>" value="<%=value1%>" name="<%= hex.toUpperCase()%>0" readonly /><br>
                                        <%= hex.toUpperCase()%>8: <input type="text" id="M<%= k + 1%>" value="<%=value2%>" name="<%= hex.toUpperCase()%>8" readonly /><br>                        
                                        <% k += 2;
                                            }%>
                                    </div></th>
                            </tr>
                        </tbody>
                    </table>
                </div>

                <!-- RUN TAB -->
                <div id="run" class="tab-pane">
                    <h3>RUN</h3>
                    <form name="input-form" method="post" action="getInput">
                        <%
                            String pcVal = (String) request.getAttribute("pcVal");
                            if(pcVal == null)
                                pcVal = "1000";
                            
                            int cyclenum;
                            if(pcVal == "1000")
                                cyclenum = 1;
                            else
                                cyclenum = (int) request.getAttribute("cyclenum");
                        %>
                        <input type="hidden" name="pcVal" value="<%= pcVal %>">
                        <input type="hidden" name="cyclenum" value="<%= cyclenum %>">
                        <table class="table">
                            <thead>
                                <tr>
                                    <th>Pipeline Map</th>
                                    <th class="col-md-2">GP Registers</th>
                                </tr>
                            </thead>
                            <tbody>
                                <tr>
                                    <th>
                                        <table class="table">
                                            <thead>
                                                <tr>
                                                    <th class="col-md-4">Instructions</th>
                                                    <th>1</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <tr>
                                                    <td>DADDIU</td>
                                                    <td>IF</td>
                                                </tr>
                                            </tbody>
                                        </table>
                                    </th>
                                    <th>
                                        <table class="table">
                                            <tr>
                                                <td class="col-md-1">R1</td>
                                                <td>0000000000000000</td>
                                            </tr>
                                        </table>
                                    </th>
                                </tr>
                            </tbody>
                        </table>

                        <table class="table">
                            <thead>
                                <tr>
                                    <th>Internal Registers</th>
                                    <th class="col-md-2">Memory</th>
                                </tr>
                            </thead>
                            <tbody>
                                <tr>
                                    <th>
                                        <table class="table">
                                            <thead>
                                                <tr>
                                                    <th class="col-md-4">Instructions</th>
                                                    <th>1</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <tr>
                                                    <td>DADDIU</td>
                                                    <td>IF</td>
                                                </tr>
                                            </tbody>
                                        </table>
                                    </th>
                                    <th>
                                        <table class="table">
                                            <tr>
                                                <td class="col-md-1">0000</td>
                                                <td>0000000000000000</td>
                                            </tr>
                                        </table>
                                    </th>
                                </tr>
                            </tbody>
                        </table>

                        <div class="center-div">
                            <input type="button" value="Run Single" class="btn" onclick="runSingle('<%= c%>');" />
                            <input type="button" value="Run Continuous" class="btn" onclick="runContinuous();"/>
                        </div>                        
                    </form>
                </div>
            </div>
        </div>
    </body>
</html>

