<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge, chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=0">
    <title>Elevator</title>
    <link rel="stylesheet" type="text/css" href="./elevator.css">
</head>
<body>
    <div class="insideView">
        <div class="elevator" id="E0">
            <div class="door leftdoor"></div>
            <div class="door rightdoor"></div>
            <div class="panel leftpanel">
                <table id="outsideUpDownButtons" cellspacing=0>
                    <tr class="floor" id="floor20">
                        <td class="button" style="border: 0px"></td>
                        <td class="godown button">&#9660;</td>
                        <td>F20</td>
                    </tr>
                    <tr class="floor" id="floor19">
                        <td class="goup button">&#9650;</td>
                        <td class="godown button">&#9660;</td>
                        <td>F19</td>
                    </tr>
                    <tr class="floor" id="floor18">
                        <td class="goup button">&#9650;</td>
                        <td class="godown button">&#9660; </td>
                        <td>F18</td>
                    </tr>
                    <tr class="floor" id="floor17">
                        <td class="goup button">&#9650;</td>
                        <td class="godown button">&#9660;</td>
                        <td>F17</td>
                    </tr>
                    <tr class="floor" id="floor16">
                        <td class="goup button">&#9650;</td>
                        <td class="godown button">&#9660;</td>
                        <td>F16</td>
                    </tr>
                    <tr class="floor" id="floor15">
                        <td class="goup button">&#9650;</td>
                        <td class="godown button">&#9660;</td>
                        <td>F15</td>
                    </tr>
                    <tr class="floor" id="floor14">
                        <td class="goup button">&#9650;</td>
                        <td class="godown button">&#9660;</td>
                        <td>F14</td>
                    </tr>
                    <tr class="floor" id="floor13">
                        <td class="goup button">&#9650;</td>
                        <td class="godown button">&#9660;</td>
                        <td>F13</td>
                    </tr>
                    <tr class="floor" id="floor12">
                        <td class="goup button">&#9650;</td>
                        <td class="godown button">&#9660;</td>
                        <td>F12</td>
                    </tr>
                    <tr class="floor" id="floor11">
                        <td class="goup button">&#9650;</td>
                        <td class="godown button">&#9660;</td>
                        <td>F11</td>
                    </tr>
                    <tr class="floor" id="floor10">
                        <td class="goup button">&#9650;</td>
                        <td class="godown button">&#9660;</td>
                        <td>F10</td>
                    </tr>
                    <tr class="floor" id="floor9">
                        <td class="goup button">&#9650;</td>
                        <td class="godown button">&#9660;</td>
                        <td>F9</td>
                    </tr>
                    <tr class="floor" id="floor8">
                        <td class="goup button">&#9650;</td>
                        <td class="godown button">&#9660;</td>
                        <td>F8</td>
                    </tr>
                    <tr class="floor" id="floor7">
                        <td class="goup button">&#9650;</td>
                        <td class="godown button">&#9660;</td>
                        <td>F7</td>
                    </tr>
                    <tr class="floor" id="floor6">
                        <td class="goup button">&#9650;</td>
                        <td class="godown button">&#9660;</td>
                        <td>F6</td>
                    </tr>
                    <tr class="floor" id="floor5">
                        <td class="goup button">&#9650;</td>
                        <td class="godown button">&#9660;</td>
                        <td>F5</td>
                    </tr>
                    <tr class="floor" id="floor4">
                        <td class="goup button">&#9650;</td>
                        <td class="godown button">&#9660;</td>
                        <td>F4</td>
                    </tr>
                    <tr class="floor" id="floor3">
                        <td class="goup button">&#9650;</td>
                        <td class="godown button">&#9660;</td>
                        <td>F3</td>
                    </tr>
                    <tr class="floor" id="floor2">
                        <td class="goup button">&#9650;</td>
                        <td class="godown button">&#9660;</td>
                        <td>F2</td>
                    </tr>
                    <tr class="floor" id="floor1">
                        <td class="goup button">&#9650;</td>
                        <td>F1</td>
                    </tr>
                </table>
            </div>
            <div class="panel rightpanel">
                <div class="screen">
                    <div class="updownlight">
                        <div id="uplight0">&#9650;</div>
                        <div id="downlight0">&#9660;</div>
                    </div>
                    <!--显示屏上的数字-->
                    <p class="floorOnScreen" id="floorOnScreen0">1</p>
                </div>
                <div class="buttons">
                    <div class="button call">&#9990;</div>
                    <div class="button emergency">&#9888;</div>
                    <div class="button open" id="openE0">&lt;|&gt;</div>
                    <div class="button close" id="closeE0">&gt;|&lt;</div>
                    <div class="dial" id="dialpad0">
                        <div class="button dial19">19</div>
                        <div class="button dial20">20</div>
                        <div class="button dial17">17</div>
                        <div class="button dial18">18</div>
                        <div class="button dial15">15</div>
                        <div class="button dial16">16</div>
                        <div class="button dial13">13</div>
                        <div class="button dial14">14</div>
                        <div class="button dial11">11</div>
                        <div class="button dial12">12</div>
                        <div class="button dial9">9</div>
                        <div class="button dial10">10</div>
                        <div class="button dial7">7</div>
                        <div class="button dial8">8</div>
                        <div class="button dial5">5</div>
                        <div class="button dial6">6</div>
                        <div class="button dial3">3</div>
                        <div class="button dial4">4</div>
                        <div class="button dial1">1</div>
                        <div class="button dial2">2</div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>
</html>