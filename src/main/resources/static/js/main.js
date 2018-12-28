var zoneSize = 200;

var parkings = [];
var parkingSize = 10;

var addZone = false;
var addParking = false;

$(document).ready(function () {
    getList();
});

$(document).on('touchstart click', '#addZone', function () {
    addZone = true;
    addParking = false;
    $("#zones_place").addClass('edit');
    $.each($('.zone'), function (k, v) {
        $(v).removeClass('edit');
    });
});

$(document).on('touchstart click', '#addParking', function () {
    addZone = false;
    addParking = true;
    $("#zones_place").removeClass('edit');
    $.each($('.zone'), function (k, v) {
        $(v).addClass('edit');
    });
});

$(document).on('touchstart click', '#zones_place', function (e) {
    if (addZone) {
        var coords = getMousePos(this, e);
        var zonename = prompt("Введите название зоны", "Zone");
        var zoneSizeX = prompt("Введите длину зоны", zoneSize);
        var zoneSizeY = prompt("Введите высоту зоны", zoneSize);
        if (zonename != null && zonename != "" && zoneSizeX != null && zoneSizeX != "" && zoneSizeY != null && zoneSizeY != "") {
            var aboutZone = {
                name: zonename,
                id: zonename.replace(" ", ""),
                x: coords.x,
                y: coords.y,
                x2: parseFloat(coords.x) + parseFloat(zoneSizeX),
                y2: parseFloat(coords.y) + parseFloat(zoneSizeY),
                sizeX: zoneSizeX,
                sizeY: zoneSizeY
            };
            console.log(aboutZone);
            drawZone(aboutZone);
            saveZone(aboutZone);
            addZone = false;
            $("#zones_place").removeClass('edit');
        }
    }
});

$(document).on('touchstart click', '.zone', function (e) {
    if (addParking) {
        var coords = getMousePos($('#zones_place'), e);
        var aboutParking = {x: coords.x, y: coords.y, isFree: true};
        drawParking(aboutParking);
        parkings.push(aboutParking);
        addParking = false;
        $.each($('.zone'), function (k, v) {
            $(v).removeClass('edit');
        });
    }
});

function getList() {
    var http = new XMLHttpRequest();
    http.open('GET', "/api/area", true);
    http.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    http.send(null);
    http.onreadystatechange = function () {
        if (http.readyState == 4) {
            if (http.status == 200) {
                var response = http.responseText;
                var json = JSON.parse(response);
                $.each(json, function (k, v) {
                    var x,y,x2,y2;

                    if(v.zoneCoordinate.point[0].x > v.zoneCoordinate.point[1].x) {
                        x = v.zoneCoordinate.point[1].x;
                        x2 = v.zoneCoordinate.point[0].x;
                    } else {
                        x = v.zoneCoordinate.point[0].x;
                        x2 = v.zoneCoordinate.point[1].x;
                    }

                    if(v.zoneCoordinate.point[0].y > v.zoneCoordinate.point[1].y) {
                        y = v.zoneCoordinate.point[1].y;
                        y2 = v.zoneCoordinate.point[0].y;
                    } else {
                        y = v.zoneCoordinate.point[0].y;
                        y2 = v.zoneCoordinate.point[1].y;
                    }

                    var aboutZone = {
                        name: v.name,
                        id: v.name.replace(" ", ""),
                        x: x,
                        y: y,
                        x2: x2,
                        y2: y2,
                        sizeX: parseFloat(x2) - parseFloat(x),
                        sizeY: parseFloat(y2) - parseFloat(y)
                    };
                    drawZone(aboutZone);
                });
            }
        }
    };
}

function drawZone(params) {
    console.log(params);
    $("#zones_place").append('<div class="zone" id="' + params.id + '" style="width: ' + Math.abs(params.sizeX) + 'px; height: ' + Math.abs(params.sizeY) + 'px; margin-left: ' + params.x + 'px; margin-top: ' + params.y + 'px;"></div>');
}

function drawParking(params) {
    var isFree = 'free';
    if (!params.isFree) {
        isFree = 'not_free';
    }

    $("#zones_place").append('<div class="parking ' + isFree + '" id="' + params.id + '" style="width: ' + parkingSize + 'px; height: ' + parkingSize + 'px; margin-left: ' + params.x + 'px; margin-top: ' + params.y + 'px;"></div>');
}

function getMousePos(div, e) {
    var offset = $(div).offset();
    return {
        x: e.pageX - offset.left,
        y: e.pageY - offset.top
    };
}

function saveZone(params) {
    var http = new XMLHttpRequest();
    http.open('POST', '/api/area', true);
    http.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    var body = 'name=' + params.name + '&freeSpaceCount=0' + '&zoneCoordinate=(' + params.x + ',' + params.y + '),(' + params.x2 + ',' + params.y2 + ')';
    http.send(body);
    http.onreadystatechange = function () {
        if (http.readyState == 4) {
            if (http.status == 200) {
            }
        }
    };
}

//Обработчик сохранения изменений
/*$(document).on('touchstart click', '.action-save', function () {
    var name = $("#currency-" + idCache).children("td")[0];
    var value = $("#currency-" + idCache).children("td")[1];
    var nameVal = $($(name).children("input")).val();
    var valueVal = $($(value).children("input")).val();
    var http = new XMLHttpRequest();
    http.open('PUT', apiRoot + nameCache, true);
    http.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    var body = 'Name=' + nameVal + '&LastValue=' + valueVal;
    http.send(body);

    http.onreadystatechange = function () {
        if (http.readyState == 4) {
            if (http.status == 200) {
                $(name).empty().html(nameVal);
                $(value).empty().html(valueVal);
                clearCache();
            }
        }
    };
});*/