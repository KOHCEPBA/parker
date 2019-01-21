var searchLimit = 1;
var searchLimitBuff = searchLimit;

var deletePoly = false;

var map;
var lastCoords;
var lastPoly;
var lastPoint;
var tempPoly = null;

function init() {
    map = new ymaps.Map('map', {
        center: [51.53295148560136, 46.00563500904242],
        zoom: 19
    });
    getList();

    map.events.add('click', function (e) {
        var coords = e.get('coords');
        pointTemp(coords);
        lastPoly = null;
    });
}

$(document).on('touchstart click', '#searchByAddressButton', function () {
    deletePoly = false;
    $('#searchModalZone').modal('show');
    return false;
});

$(document).on('touchstart click', '#searchParkingsByZoneButton', function () {
    searchPlaces();
    return false;
});

$(document).on('touchstart click', '#searchParkingsButton', function () {
    searchPlaces();
    return false;
});

$(document).on('touchstart click', '#clearParkingsButton', function () {
    clearFoundPlaces();
    return false;
});

$(document).on('touchstart click', '#addZone', function () {
    deletePoly = false;
    $('#zonesButton').hide();
    $('#addParking').hide();

    $('#saveZoneH').show();
    $('#cancelZoneH').show();

    tempPoly = new ymaps.Polygon([], {}, {
        editorDrawingCursor: "crosshair",
        fillColor: '#aeff56',
        fillOpacity: 0.5,
        strokeColor: '#3c3c3c',
        strokeWidth: 3
    });

    if (lastCoords) {
        map.geoObjects.remove(lastCoords);
        lastCoords = null;
    }

    map.geoObjects.add(tempPoly);
    tempPoly.editor.startDrawing();
});

$(document).on('touchstart click', '#saveZoneH', function () {
    if (tempPoly.geometry.getCoordinates()[0].length > 3) {
        $.each($('input[id*="editModalZone"]'), function (k, v) {
            $(v).val('');
        });
        $('#editModalZone').modal("show");
    } else {
        alert("Необходимо указать более 3 точек");
    }
    return false;
});

$(document).on('touchstart click', '#cancelZoneH', function () {
    $('#zonesButton').show();
    $('#addParking').show();

    $('#saveZoneH').hide();
    $('#cancelZoneH').hide();

    tempPoly.editor.stopDrawing();
    map.geoObjects.remove(tempPoly);
    return false;
});

$(document).on('touchstart click', '#addParking', function () {
    deletePoly = false;
    if (lastPoly) {
        var c = lastCoords.geometry.getCoordinates();
        var params = {
            areaID: lastPoly.properties.get("areaID"),
            isFree: true,
            coords: c
        };
        saveParking(params);
    }
    return false;
});

$(document).on('touchstart click', '#deleteZone', function (e) {
    deletePoly = true;
});

$(document).on('touchstart click', '#deleteButton', function (e) {
    if (lastPoint) {
        deleteParking();
    }
    return false;
});

$(document).on('touchstart click', '#editButton', function (e) {
    if (lastPoint) {
        $('#editModalParking').modal('show');
    }
    return false;
});

$(document).on('touchstart click', '#saveZone', function (e) {
    var zoneCountry = $('#editModalZoneCountry').val();
    var zoneRegion = $('#editModalZoneRegion').val();
    var zoneCity = $('#editModalZoneCity').val();
    var zoneStreet = $('#editModalZoneStreet').val();
    var zoneNumber = $('#editModalZoneNumber').val();
    var error = false;

    $('#editModalZoneCountry').removeClass('error');
    $('#editModalZoneRegion').removeClass('error');
    $('#editModalZoneCity').removeClass('error');
    $('#editModalZoneStreet').removeClass('error');
    $('#editModalZoneNumber').removeClass('error');


    if (zoneCountry == '' || zoneCountry == undefined) {
        error = true;
        $('#editModalZoneCountry').addClass('error');
    }

    if (zoneRegion == '' || zoneRegion == undefined) {
        error = true;
        $('#editModalZoneRegion').addClass('error');
    }

    if (zoneCity == '' || zoneCity == undefined) {
        error = true;
        $('#editModalZoneCity').addClass('error');
    }

    if (zoneStreet == '' || zoneStreet == undefined) {
        error = true;
        $('#editModalZoneStreet').addClass('error');
    }

    if (zoneNumber == '' || zoneNumber == undefined) {
        error = true;
        $('#editModalZoneNumber').addClass('error');
    }

    if (!error) {
        var coords = "(";
        var c = tempPoly.geometry.getCoordinates()[0];
        var first = true;
        $.each(c, function (k, v) {
            if (first) {
                first = false;
            } else {
                coords = coords + ",";
            }
            coords = coords + "(" + v[0] + "," + v[1] + ")";
        });
        coords = coords + ")";

        var addr = {
            country: zoneCountry,
            region: zoneRegion,
            city: zoneCity,
            street: zoneStreet,
            number: zoneNumber
        };
        var aboutZone = {
            geoAddress: addr,
            polygonCoordinate: coords,
            coords: c
        };
        saveZone(aboutZone);

        $('#editModalZone').modal('hide');
    }
    return false;
});

$(document).on('touchstart click', '#saveParking', function (e) {
    var state = $('#editModalParkingState').val() == "true";
    if (state != lastPoint.properties.get('isFree')) {
        editParking();
    }
    $('#editModalParking').modal('hide');
    return false;
});

$(document).on('touchstart click', '#searchZone', function (e) {

    var addressTamplate = [];

    if ($('#searchModalZoneCountry').val() != '') {
        addressTamplate.push({
            "field": "country",
            "conformityMode": $('#searchModalZoneCountry_select').val(),
            "value": $('#searchModalZoneCountry').val()
        });
    }

    if ($('#searchModalZoneRegion').val() != '') {
        addressTamplate.push({
            "field": "region",
            "conformityMode": $('#searchModalZoneRegion_select').val(),
            "value": $('#searchModalZoneRegion').val()
        });
    }

    if ($('#searchModalZoneCity').val() != '') {
        addressTamplate.push({
            "field": "city",
            "conformityMode": $('#searchModalZoneCity_select').val(),
            "value": $('#searchModalZoneCity').val()
        });
    }

    if ($('#searchModalZoneStreet').val() != '') {
        addressTamplate.push({
            "field": "street",
            "conformityMode": $('#searchModalZoneStreet_select').val(),
            "value": $('#searchModalZoneStreet').val()
        });
    }

    if ($('#searchModalZoneNumber').val() != '') {
        addressTamplate.push({
            "field": "number",
            "conformityMode": $('#searchModalZoneNumber_select').val(),
            "value": $('#searchModalZoneNumber').val()
        });
    }

    var http = new XMLHttpRequest();
    http.open('POST', "/api/area/addressTamplate", true);
    http.setRequestHeader('Content-Type', 'application/json');
    http.send(JSON.stringify(addressTamplate));
    http.onreadystatechange = function () {
        if (http.readyState == 4) {
            if (http.status == 200) {
                var response = http.responseText;
                var json = JSON.parse(response);
                var result = ymaps.geoQuery(map.geoObjects);
                var r = result.search('geometry.type = "Polygon"');
                r.then(function () {
                    var r2 = r.search('properties.founded = true');
                    r2.then(function () {
                        r2.setOptions({
                            fillColor: '#71afff'
                        });
                        r2.setProperties('founded', false);
                    });
                    $.each(json, function (k, v) {
                        var r3 = r.search('properties.areaID = ' + v.id);
                        r3.then(function () {
                            r3.setOptions({
                                fillColor: '#ff1934'
                            });
                            r3.setProperties('founded', true);
                        });
                    });
                });
            }
        }
    };

    $('#searchModalZone').modal('hide');
    return false;
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
                    var points = [];
                    $.each(v.polygonCoordinate.points, function (k1, v1) {
                        points.push([v1.x, v1.y]);
                    });
                    addPoly(points, v.id, v.freeSpaceCount);
                    $.each(v.places, function (k1, v1) {
                        addPoint([v1.coordinate.x, v1.coordinate.y], v.id, v1.id, v1.isFree);
                    });
                });
            }
        }
    };
}

function saveZone(params) {
    var http = new XMLHttpRequest();
    http.open('POST', '/api/area', true);
    http.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    var body = 'geoAddress.country=' + params.geoAddress.country + '&geoAddress.region=' + params.geoAddress.region +
        '&geoAddress.city=' + params.geoAddress.city + '&geoAddress.street=' + params.geoAddress.street +
        '&geoAddress.number=' + params.geoAddress.number + '&freeSpaceCount=0' + '&polygonCoordinate=' + params.polygonCoordinate;
    http.send(body);
    http.onreadystatechange = function () {
        if (http.readyState == 4) {
            if (http.status == 201) {
                var response = http.responseText;
                var json = JSON.parse(response);
                tempPoly.editor.stopDrawing();
                map.geoObjects.remove(tempPoly);
                tempPoly = null;
                addPoly(params.coords, json.id, json.freeSpaceCount);
                $('#zonesButton').show();
                $('#addParking').show();

                $('#saveZoneH').hide();
                $('#cancelZoneH').hide();
            }
        }
    };
}

function saveParking(params) {
    var http = new XMLHttpRequest();
    http.open('POST', '/api/place', true);
    http.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    var body = 'area.id=' + params.areaID + '&isFree=' + params.isFree + '&coordinate=(' +
        params.coords[0] + ',' + params.coords[1] + ')';
    http.send(body);
    http.onreadystatechange = function () {
        if (http.readyState == 4) {
            if (http.status == 201) {
                var response = http.responseText;
                var json = JSON.parse(response);
                params.id = json.id;
                if (lastCoords) {
                    map.geoObjects.remove(lastCoords);
                    lastCoords = null;
                }
                lastPoly = null;
                addPoint(params.coords, params.areaID, json.id, params.isFree);
            }
        }
    };
}

function editParking() {
    var http = new XMLHttpRequest();
    http.open('PUT', '/api/place/' + lastPoint.properties.get('pointID'), true);
    http.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    var body = 'isFree=' + $('#editModalParkingState').val();
    http.send(body);
    http.onreadystatechange = function () {
        if (http.readyState == 4) {
            if (http.status == 200) {
                var response = http.responseText;
                var json = JSON.parse(response);
                lastPoint.properties.set("selected", false);
                lastPoint.options.set("preset", json.isFree ? "islands#blueParkingCircleIcon" : "islands#redParkingCircleIcon");
                lastPoint = null;
                $('#actionsDropdown').hide();
            }
        }
    };
}

function deleteZone(poly) {
    var id = poly.properties.get("areaID");
    var http = new XMLHttpRequest();
    http.open('DELETE', '/api/area/' + id, true);
    http.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    http.send(null);
    http.onreadystatechange = function () {
        if (http.readyState == 4) {
            if (http.status == 200) {
                if (lastPoint) {
                    lastPoint.properties.set("selected", false);
                    lastPoint.options.set("preset", lastPoint.properties.get("isFree") ? "islands#blueParkingCircleIcon" : "islands#redParkingCircleIcon");
                    lastPoint = null;
                    $('#actionsDropdown').hide();
                }
                var result = ymaps.geoQuery(map.geoObjects);
                var r = result.search('geometry.type = "Point"');
                r.then(function () {
                    var r2 = r.search('properties.areaID = ' + id);
                    r2.then(function () {
                        r2.removeFromMap(map);
                    });
                });
                map.geoObjects.remove(poly);
            }
        }
    };
}

function deleteParking() {
    var http = new XMLHttpRequest();
    http.open('DELETE', '/api/place/' + lastPoint.properties.get("pointID"), true);
    http.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    http.send(null);
    http.onreadystatechange = function () {
        if (http.readyState == 4) {
            if (http.status == 200) {
                map.geoObjects.remove(lastPoint);
                lastPoint = null;
                $('#actionsDropdown').hide();
            }
        }
    };
}

function searchPlaces() {
    if (lastCoords) {
        clearFoundPlaces();
        var coords = lastCoords.geometry.getCoordinates();
        var http = new XMLHttpRequest();
        http.open('POST', '/api/place/nearest_free_spaces/' + searchLimitBuff, true);
        http.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
        var body = 'coordinate=(' + coords[0] + ', ' + coords[1] + ')';
        http.send(body);
        http.onreadystatechange = function () {
            if (http.readyState == 4) {
                if (http.status == 200) {
                    var response = http.responseText;
                    var json = JSON.parse(response);
                    if (json.length == 0) {
                        alert('Места не найдены');
                    } else {
                        var result = ymaps.geoQuery(map.geoObjects);
                        var r = result.search('geometry.type = "Point"');
                        r.then(function () {
                            $.each(json, function (k, v) {
                                var r2 = r.search('properties.pointID = ' + v.id).search('properties.isFree = true');
                                r2.then(function () {
                                    r2.setOptions({
                                        preset: 'islands#blueParkingIcon'
                                    });
                                });
                            });
                        });
                        map.setCenter(coords);
                    }
                }
            }
        };
    }
}

function searchPlacesByName(name) {
    var http = new XMLHttpRequest();
    http.open('POST', '/api/place/area_places/' + name, true);
    http.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    var body = '';
    http.send(body);
    http.onreadystatechange = function () {
        if (http.readyState == 4) {
            if (http.status == 200) {
                var response = http.responseText;
                var json = JSON.parse(response);
                if (json.length == 0) {
                    alert('Места не найдены');
                } else {
                    clearFoundPlaces();
                    $.each(json, function (k, v) {
                        $('div[park-id=' + v.id + ']').addClass('found');
                    });
                }
            }
        }
    };
}

function clearFoundPlaces() {
    var result = ymaps.geoQuery(map.geoObjects);
    var r = result.search('geometry.type = "Point"');
    r.then(function () {
        var r2 = r.search('properties.pointID != null').search('properties.isFree = true');
        r2.then(function () {
            r2.setOptions({
                preset: 'islands#blueParkingCircleIcon'
            });
        });
    });
}

function addPoly(points, id, freeSpaceCount) {
    var poly = new ymaps.Polygon([points], {
        areaID: id,
        hintContent: "Свободных мест: " + freeSpaceCount,
        founded: false
    }, {
        editorDrawingCursor: "crosshair",
        fillColor: '#71afff',
        fillOpacity: 0.5,
        strokeColor: '#3c3c3c',
        strokeWidth: 3
    });
    map.geoObjects.add(poly);
    poly.events.add('click', function (e) {
        if (!deletePoly) {
            var coords = e.get('coords');
            pointTemp(coords);
            lastPoly = poly;
        } else if (deletePoly) {
            var answ = confirm("Вы точно хотите удалить зону парковки и все парковочные места?");
            if (answ) {
                deleteZone(e.get("target"));
            }
        }
    });
}

function addPoint(coords, id, idPoint, isFree) {
    var point = new ymaps.GeoObject({
        geometry: {
            type: "Point",
            coordinates: coords
        },
        properties: {
            hintContent: isFree ? "Свободно" : "Занято",
            areaID: id,
            pointID: idPoint,
            isFree: isFree,
            selected: false
        }
    }, {
        preset: isFree ? "islands#blueParkingCircleIcon" : "islands#redParkingCircleIcon",
        draggable: false
    });
    map.geoObjects.add(point);
    point.events.add('click', function (e) {
        var c = e.get('target');
        if (c != lastPoint) {
            if (lastCoords) {
                map.geoObjects.remove(lastCoords);
                lastCoords = null;
            }
            if (lastPoint) {
                lastPoint.properties.set("selected", false);
                lastPoint.options.set("preset", lastPoint.properties.get("isFree") ? "islands#blueParkingCircleIcon" : "islands#redParkingCircleIcon");
            }
            lastPoint = c;
            c.properties.set("selected", true);
            c.options.set("preset", c.properties.get("isFree") ? "islands#blueParkingIcon" : "islands#redParkingIcon");
            $('#actionsDropdown').show();
        } else {
            lastPoint = null;
            c.properties.set("selected", false);
            c.options.set("preset", c.properties.get("isFree") ? "islands#blueParkingCircleIcon" : "islands#redParkingCircleIcon");
            $('#actionsDropdown').hide();
        }
    });
}

function pointTemp(coord) {
    if (!lastPoint) {
        if (lastCoords) {
            lastCoords.geometry.setCoordinates(coord);
        } else {
            lastCoords = new ymaps.Placemark(coord, {
                iconCaption: 'Я здесь'
            }, {
                preset: 'islands#violetDotIconWithCaption',
                draggable: true
            });
            map.geoObjects.add(lastCoords);
        }
    }
}