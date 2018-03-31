<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="Model.*"%>
<%@page import="java.util.*"%>

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
    </head>
    <body>
        <div>
            <ul class="nav nav-tabs">
                <li class="active"><a data-toggle="tab" href="#home">Home</a></li>
                <li><a data-toggle="tab" href="#input">Input</a></li>
                <li><a data-toggle="tab" href="#view">View</a></li>
                <li><a data-toggle="tab" href="#run">Run</a></li>
            </ul>

            <div class="tab-content">

                <!-- HOME TAB -->
                <div id="home" class="tab-pane in active">
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
                <div id="input" class="tab-pane">
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
                                            for(int i = 0; i < inst.size(); i++)
                                                out.print(inst.get(i));
                                        }
                                            %></textarea></th>
                                    <th>
                                        <div style="overflow-y:scroll;height:305px;display:block;">
                                            <table>
                                                <%  ArrayList<GPRegs> regs = (ArrayList<GPRegs>) request.getAttribute("regs"); 
                                                    String[] regValue = new String[32];
                                                    if(regs == null)
                                                        for(int i = 0; i < 32; i++){
                                                            regValue[i] = "0000000000000000";
                                                        }
                                                    else
                                                        for(int i = 0; i < 32; i++){
                                                            regValue[i] = regs.get(i).getReg();
                                                        }
                                                %>
                                                <tr><th>R0:</th> <th><input type="text" name="R0" value="0000000000000000" readonly></th></tr>
                                                <tr><th>R1:</th> <th><input type="text" name="R1" value="<%= regValue[1]%>"></th></tr>
                                                <tr><th>R2:</th> <th><input type="text" name="R2" value="<%= regValue[2]%>"></th></tr>
                                                <tr><th>R3:</th> <th><input type="text" name="R3" value="<%= regValue[3]%>"></th></tr>
                                                <tr><th>R4:</th> <th><input type="text" name="R4" value="<%= regValue[4]%>"></th></tr>
                                                <tr><th>R5:</th> <th><input type="text" name="R5" value="<%= regValue[5]%>"></th></tr>
                                                <tr><th>R6:</th> <th><input type="text" name="R6" value="<%= regValue[6]%>"></th></tr>
                                                <tr><th>R7:</th> <th><input type="text" name="R7" value="<%= regValue[7]%>"></th></tr>
                                                <tr><th>R8:</th> <th><input type="text" name="R8" value="<%= regValue[8]%>"></th></tr>
                                                <tr><th>R9:</th> <th><input type="text" name="R9" value="<%= regValue[9]%>"></th></tr>
                                                <tr><th>R10:</th> <th><input type="text" name="R10" value="<%= regValue[10]%>"></th></tr>
                                                <tr><th>R11:</th> <th><input type="text" name="R11" value="<%= regValue[11]%>"></th></tr>
                                                <tr><th>R12:</th> <th><input type="text" name="R12" value="<%= regValue[12]%>"></th></tr>
                                                <tr><th>R13:</th> <th><input type="text" name="R13" value="<%= regValue[13]%>"></th></tr>
                                                <tr><th>R14:</th> <th><input type="text" name="R14" value="<%= regValue[14]%>"></th></tr>
                                                <tr><th>R15:</th> <th><input type="text" name="R15" value="<%= regValue[15]%>"></th></tr>
                                                <tr><th>R16:</th> <th><input type="text" name="R16" value="<%= regValue[16]%>"></th></tr>
                                                <tr><th>R17:</th> <th><input type="text" name="R17" value="<%= regValue[17]%>"></th></tr>
                                                <tr><th>R18:</th> <th><input type="text" name="R18" value="<%= regValue[18]%>"></th></tr>
                                                <tr><th>R19:</th> <th><input type="text" name="R19" value="<%= regValue[19]%>"></th></tr>
                                                <tr><th>R20:</th> <th><input type="text" name="R20" value="<%= regValue[20]%>"></th></tr>
                                                <tr><th>R21:</th> <th><input type="text" name="R21" value="<%= regValue[21]%>"></th></tr>
                                                <tr><th>R22:</th> <th><input type="text" name="R22" value="<%= regValue[22]%>"></th></tr>
                                                <tr><th>R23:</th> <th><input type="text" name="R23" value="<%= regValue[23]%>"></th></tr>
                                                <tr><th>R24:</th> <th><input type="text" name="R24" value="<%= regValue[24]%>"></th></tr>
                                                <tr><th>R25:</th> <th><input type="text" name="R25" value="<%= regValue[25]%>"></th></tr>
                                                <tr><th>R26:</th> <th><input type="text" name="R26" value="<%= regValue[26]%>"></th></tr>
                                                <tr><th>R27:</th> <th><input type="text" name="R27" value="<%= regValue[27]%>"></th></tr>
                                                <tr><th>R28:</th> <th><input type="text" name="R28" value="<%= regValue[28]%>"></th></tr>
                                                <tr><th>R29:</th> <th><input type="text" name="R29" value="<%= regValue[29]%>"></th></tr>
                                                <tr><th>R30:</th> <th><input type="text" name="R30" value="<%= regValue[30]%>"></th></tr>
                                                <tr><th>R31:</th> <th><input type="text" name="R31" value="<%= regValue[31]%>"></th></tr>
                                            </table>
                                        </div>
                                    </th>
                                    <th>
                                        <div style="overflow-y:scroll;height:305px;display:block;">
                                            <%
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
                                            %>
                                            <%= hex.toUpperCase()%>0: <input type="text" value="0000000000000000" name="<%= hex.toUpperCase()%>0" /><br>
                                            <%= hex.toUpperCase()%>8: <input type="text" value="0000000000000000" name="<%= hex.toUpperCase()%>8" /><br>                        
                                            <%}%>
                                        </div></th>
                                </tr>
                            </tbody>
                        </table>

                        <div class="center-div">
                            <input type="submit" value="Load" class="btn" />
                            <input type="submit" value="Reset" class="btn" />
                        </div>
                    </form>

                    <table class="table">
                        <tbody>
                            <tr>
                                <th><textarea id="error" rows="4"><%
                                        ArrayList<Errors> e = (ArrayList<Errors>) request.getAttribute("errors");
                                        if (e != null) {
                                            for(int i = 0; i < e.size(); i++)
                                                out.println(e.get(i).getError());
                                        }
                                        
                                        ArrayList<String> errReg = (ArrayList<String>) request.getAttribute("errReg");
                                        if(errReg != null) {
                                            for(int i = 0; i < errReg.size(); i++){
                                                out.println(errReg.get(i));
                                            }
                                        }
                                            %></textarea></th>
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
                                                <!--<td>Instruction</td>
                                                <td>R31-26</td>
                                                <td>R25-21</td>
                                                <td>R20-16</td>
                                                <td>R15-0</td>
                                                <td>Hex</td>-->
                                                <td>
                                                    <%
                                                        ArrayList<Code> c = (ArrayList<Code>) request.getAttribute("code");
                                                        if (c != null) {
                                                            out.print(c.get(0).getOpcode());
                                                        }
                                                    %>
                                                </td>
                                            </tr>
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
                                    <table class="table">
                                        <thead>
                                            <tr>
                                                <th>Register</th>
                                                <th>Value</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <tr>
                                                <td>Register</td>
                                                <td>Value</td>
                                            </tr>
                                        </tbody>
                                    </table>
                                </th>
                                <th>
                                    <table class="table">
                                        <thead>
                                            <tr>
                                                <th>Memory Address</th>
                                                <th>Value</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <tr>
                                                <td>Memory Address</td>
                                                <td>Value</td>
                                            </tr>
                                        </tbody>
                                    </table>
                                </th>
                            </tr>
                        </tbody>
                    </table>
                </div>

                <!-- RUN TAB -->
                <div id="run" class="tab-pane">
                    <h3>RUN</h3>
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
                </div>
            </div>
        </div>
    </body>
</html>

