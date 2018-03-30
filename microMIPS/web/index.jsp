<%@page contentType="text/html" pageEncoding="UTF-8"%>
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
                                    <th><textarea id="codeInput" name="codeInput" rows="15"></textarea></th>
                                    <th><textarea id="regInput" name="regInput" rows="15"></textarea></th>
                                    <th><textarea id="memInput" name="memInput" rows="15"></textarea></th>
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
                                <th><textarea id="error" rows="4"></textarea></th>
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
                                                <td>Instruction</td>
                                                <td>R31-26</td>
                                                <td>R25-21</td>
                                                <td>R20-16</td>
                                                <td>R15-0</td>
                                                <td>Hex</td>
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

