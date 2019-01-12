var zoneSize = 200;
var parkingSize = 5;
var searchLimit = 3;
var searchLimitBuff = searchLimit;

var addZone = false;
var addParking = false;

var isEditPark = false;
var isEditZone = false;

var searchMode = false;
var searchModeByZone = false;

var aboutParkingV;
var zoneNameBuff;
var zoneNumberBuff;
var coordsBuff;

$(document).ready(function () {
    getList();
});

$(document).on('touchstart click', '#searchParkingsByZoneButton', function () {
    searchModeByZone = true;
    $('#zones_place').addClass('search');

    $('.parking').addClass('parking-search');
    $('.parking-search').removeClass('parking');
});

$(document).on('touchstart click', '#searchParkingsButton', function () {
    searchMode = true;
    $('#zones_place').addClass('search');

    $('.zone').addClass('zone-search');
    $('.zone-search').removeClass('zone');

    $('.parking').addClass('parking-search');
    $('.parking-search').removeClass('parking');

    searchLimitBuff = prompt("Введите количество мест", searchLimit);
});

$(document).on('touchstart click', '#clearParkingsButton', function () {
    clearFoundPlaces();
    return false;
});

$(document).on('touchstart click', '#addZone', function () {
    addZone = true;
    addParking = false;
    isEditZone = false;
    isEditPark = false;
    removeSearchMode();
    $("#zones_place").addClass('edit');
    $.each($('.zone'), function (k, v) {
        $(v).removeClass('edit');
    });
});

$(document).on('touchstart click', '#addParking', function () {
    addZone = false;
    addParking = true;
    isEditZone = false;
    isEditPark = false;
    removeSearchMode();
    $("#zones_place").removeClass('edit');
    $.each($('.zone'), function (k, v) {
        $(v).addClass('edit');
    });
});

$(document).on('touchstart click', '#zones_place', function (e) {
    if (addZone) {
        coordsBuff = getMousePos(this, e);

        $('#editModalZoneName').val("Zone");
        $('#editModalZoneNumber').val(0);
        $('.sizeZone').show();

        $('#editModalZoneXSize').val(zoneSize);
        $('#editModalZoneYSize').val(zoneSize);

        $('#editModalZone').modal('show');
    }

    if (searchMode) {
        var coords = getMousePos(this, e);
        searchPlaces(coords);
        removeSearchMode();
    }
});

$(document).on('touchstart click', '.zone', function (e) {
    if (addParking) {
        $('#editModalParking').modal('show');
        var coords = getMousePos($('#zones_place'), e);
        aboutParkingV = {
            id: 0,
            areaID: $(this).attr('zone-id'),
            x: coords.x,
            y: coords.y,
            isFree: true
        };
    }

    if (!addZone && !addParking && !searchModeByZone) {
        selectZone(this);
    }

    if (searchModeByZone) {
        searchPlacesByName($(this).attr('zone-name'));
        removeSearchMode();
    }
});

$(document).on('touchstart click', '#deleteButton', function (e) {
    var k = $('.selected');
    if (k.hasClass('zone')) {
        deleteZone(k.attr('zone-id'));
    } else {
        deleteParking(k.attr('park-id'));
    }
    return false;
});

$(document).on('touchstart click', '#editButton', function (e) {
    var k = $('.selected');
    if (k.hasClass('zone')) {
        isEditZone = true;
        zoneNameBuff = k.attr('zone-address').name;
        zoneNameBuff = k.attr('zone-address').name;
        zoneNumberBuff = k.attr('zone-address').number;
        $('#editModalZoneName').val(zoneNameBuff);
        $('#editModalZoneNumber').val(zoneNumberBuff);
        $('.sizeZone').hide();
        $('#editModalZone').modal('show');
    } else {
        isEditPark = true;
        $('#editModalParking').modal('show');
    }
    return false;
});

$(document).on('touchstart click', '#saveZone', function (e) {
    if (!isEditZone) {
        var zoneCountry = $('#editModalZoneCountry').val();
        var zoneRegion = $('#editModalZoneRegion').val();
        var zoneCity = $('#editModalZoneCity').val();
        var zoneStreet = $('#editModalZoneStreet').val();
        var zoneNumber = $('#editModalZoneNumber').val();
        var zoneSizeX = $('#editModalZoneXSize').val();
        var zoneSizeY = $('#editModalZoneYSize').val();
        var error = false;

        $('#editModalZoneCountry').removeClass('error');
        $('#editModalZoneRegion').removeClass('error');
        $('#editModalZoneCity').removeClass('error');
        $('#editModalZoneStreet').removeClass('error');
        $('#editModalZoneNumber').removeClass('error');
        $('#editModalZoneXSize').removeClass('error');
        $('#editModalZoneYSize').removeClass('error');


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

        if (zoneSizeX == '' || zoneSizeX == undefined) {
            error = true;
            $('#editModalZoneXSize').addClass('error');
        }

        if (zoneSizeY == '' || zoneSizeY == undefined) {
            error = true;
            $('#editModalZoneYSize').addClass('error');
        }

        if (!error) {
            var addr = {
                country: zoneCountry,
                region: zoneRegion,
                city: zoneCity,
                street: zoneStreet,
                number: zoneNumber
            };
            var aboutZone = {
                address: addr,
                areaID: 0,
                x: coordsBuff.x,
                y: coordsBuff.y,
                x2: parseFloat(coordsBuff.x) + parseFloat(zoneSizeX),
                y2: parseFloat(coordsBuff.y) + parseFloat(zoneSizeY),
                sizeX: zoneSizeX,
                sizeY: zoneSizeY
            };
            saveZone(aboutZone);
            addZone = false;
            $("#zones_place").removeClass('edit');
            $('#editModalZone').modal('hide');
        }
    } else {
        $('#editModalZoneName').removeClass('error');
        $('#editModalZoneNumber').removeClass('error');
        var newName = $('#editModalZoneName').val();
        var newNumber = $('#editModalZoneNumber').val();
        if (newName == '' || newName == undefined) {
            $('#editModalZoneName').addClass('error');
            return;
        }
        if (newNumber == '' || newNumber == undefined) {
            $('#editModalZoneNumber').addClass('error');
            return;
        }
        if (zoneNameBuff != newName || zoneNumberBuff != newNumber) {
            var addr = {
                name: newName,
                number: newNumber
            };
            var aboutZone = {
                id: $('.zone.selected').attr('zone-id'),
                address: addr
            };
            editZone(aboutZone);
        }
        isEditZone = false;
    }
    $('#editModalZone').modal('hide');
});

$(document).on('touchstart click', '#saveParking', function (e) {
    if (!isEditPark) {
        aboutParkingV.isFree = ($('#editModalParkingState').val() === 'true');
        saveParking(aboutParkingV);
        addParking = false;
        $.each($('.zone'), function (k, v) {
            $(v).removeClass('edit');
        });
    } else {
        var editPark = $('.parking.selected');
        var aboutParking = {
            id: editPark.attr('park-id'),
            isFree: ($('#editModalParkingState').val() === 'true')
        };
        editParking(aboutParking);
        isEditPark = false;
    }
    $('#editModalParking').modal('hide');
});

$(document).on('hidden.bs.modal', '#editModalParking', function (e) {
    addParking = false;
    $.each($('.zone'), function (k, v) {
        $(v).removeClass('edit');
    });
});

$(document).on('touchstart click', '.parking', function (e) {
    if (!addZone && !addParking) {
        selectZone(this);
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
                    var x, y, x2, y2;

                    if (v.zoneCoordinate.point[0].x > v.zoneCoordinate.point[1].x) {
                        x = v.zoneCoordinate.point[1].x;
                        x2 = v.zoneCoordinate.point[0].x;
                    } else {
                        x = v.zoneCoordinate.point[0].x;
                        x2 = v.zoneCoordinate.point[1].x;
                    }

                    if (v.zoneCoordinate.point[0].y > v.zoneCoordinate.point[1].y) {
                        y = v.zoneCoordinate.point[1].y;
                        y2 = v.zoneCoordinate.point[0].y;
                    } else {
                        y = v.zoneCoordinate.point[0].y;
                        y2 = v.zoneCoordinate.point[1].y;
                    }

                    var aboutZone = {
                        places: v.places,
                        geoAddress: v.geoAddress,
                        areaID: v.id,
                        x: x,
                        y: y,
                        x2: x2,
                        y2: y2,
                        sizeX: parseFloat(x2) - parseFloat(x),
                        sizeY: parseFloat(y2) - parseFloat(y)
                    };
                    drawZone(aboutZone);

                    $.each(aboutZone.places, function (k, v) {
                        var aboutPlace = {
                            id: v.id,
                            areaID: aboutZone.areaID,
                            isFree: v.isFree,
                            x: v.coordinate.x,
                            y: v.coordinate.y
                        };
                        drawParking(aboutPlace);
                    });
                });
            }
        }
    };
}

function drawZone(params) {
    $("#zones_place").append('<div class="zone" zone-id="' + params.areaID + '" zone-address="' +
        params.geoAddress + '"zone-places="' + params.places + '" id="zone-' + params.areaID + '" style="width: ' +
        Math.abs(params.sizeX) + 'px; height: ' + Math.abs(params.sizeY) + 'px; margin-left: ' +
        params.x + 'px; margin-top: ' + params.y + 'px;"></div>');
}

function drawParking(params) {
    params.x = params.x - (parkingSize / 2);
    params.y = params.y - (parkingSize / 2);

    var isFree = 'free';
    if (!params.isFree) {
        isFree = 'not_free';
    }

    $("#zones_place").append('<div class="parking ' + isFree + '" zone-id="' +
        params.areaID + '" park-id="' + params.id + '" id="park-' +
        params.id + '" style="width: ' + parkingSize + 'px; height: ' +
        parkingSize + 'px; margin-left: ' + params.x + 'px; margin-top: ' + params.y + 'px;"></div>');
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
    http.open('PUT', '/api/area', true);
    http.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    var body = 'geoAddress.country=' + params.address.country + '&geoAddress.region=' + params.address.region +
        '&geoAddress.city=' + params.address.city + '&geoAddress.street=' + params.address.street +
        '&geoAddress.number=' + params.address.number + '&freeSpaceCount=0' + '&zoneCoordinate=(' +
        params.x + ',' + params.y + '),(' + params.x2 + ',' + params.y2 + ')';
    http.send(body);
    http.onreadystatechange = function () {
        if (http.readyState == 4) {
            if (http.status == 200) {
                var response = http.responseText;
                var json = JSON.parse(response);
                params.areaID = json.id;
                drawZone(params);
            }
        }
    };
}

function saveParking(params) {
    var http = new XMLHttpRequest();
    http.open('PUT', '/api/place', true);
    http.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    var body = 'area.id=' + params.areaID + '&isFree=' + params.isFree + '&coordinate=(' +
        params.x + ',' + params.y + ')';
    http.send(body);
    http.onreadystatechange = function () {
        if (http.readyState == 4) {
            if (http.status == 200) {
                var response = http.responseText;
                var json = JSON.parse(response);
                params.id = json.id;
                drawParking(params);
            }
        }
    };
}

function editZone(params) {
    var http = new XMLHttpRequest();
    http.open('POST', '/api/area/' + params.id, true);
    http.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    var body = 'geoAddress.name=' + params.address.name + '&geoAddress.int_tag=' + params.address.number;
    http.send(body);
    http.onreadystatechange = function () {
        if (http.readyState == 4) {
            if (http.status == 200) {
                var response = http.responseText;
                var json = JSON.parse(response);
                var zone = $('.zone[zone-id=' + json.id + ']');
                console.log(json);
                zone.attr('zone-address', json.geoAddress);
                console.log(zone);
                zone.click();
            }
        }
    };
}

function editParking(params) {
    var http = new XMLHttpRequest();
    http.open('POST', '/api/place/' + params.id, true);
    http.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    var body = 'isFree=' + params.isFree;
    http.send(body);
    http.onreadystatechange = function () {
        if (http.readyState == 4) {
            if (http.status == 200) {
                var response = http.responseText;
                var json = JSON.parse(response);
                var isFree = 'free';
                if (!json.isFree) {
                    isFree = 'not_free';
                }
                var area = $('div[park-id=' + params.id + ']');
                area.removeClass('free');
                area.removeClass('not_free');
                area.addClass(isFree);
                area.click();
            }
        }
    };
}

function deleteZone(id) {
    var http = new XMLHttpRequest();
    http.open('DELETE', '/api/area/' + id, true);
    http.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    http.send(null);
    http.onreadystatechange = function () {
        if (http.readyState == 4) {
            if (http.status == 200) {
                $.each($('div[zone-id=' + id + ']'), function (k, v) {
                    $(v).remove();
                });
                $('#actionsDropdown').hide();
            }
        }
    };
}

function deleteParking(id) {
    var http = new XMLHttpRequest();
    http.open('DELETE', '/api/place/' + id, true);
    http.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    http.send(null);
    http.onreadystatechange = function () {
        if (http.readyState == 4) {
            if (http.status == 200) {
                var area = $('div[park-id=' + id + ']');
                area.remove();
                $('#actionsDropdown').hide();
            }
        }
    };
}

function selectZone(zone) {
    if ($(zone).hasClass('selected')) {
        $(zone).removeClass('selected');
    } else {
        $.each($('.zone'), function (k, v) {
            $(v).removeClass('selected');
        });
        $.each($('.parking'), function (k, v) {
            $(v).removeClass('selected');
        });
        $(zone).addClass('selected');
    }

    var selected = $('.selected');
    if (selected.length == 0) {
        $('#actionsDropdown').hide();
    } else {
        $('#actionsDropdown').show();
    }
}

function removeSearchMode() {
    searchMode = false;
    searchModeByZone = false;
    $('#zones_place').removeClass('search');

    $('.zone-search').addClass('zone');
    $('.zone').removeClass('zone-search');

    $('.parking-search').addClass('parking');
    $('.parking').removeClass('parking-search');
}

function searchPlaces(coords) {
    var http = new XMLHttpRequest();
    http.open('POST', '/api/place/nearest_free_spaces/' + searchLimitBuff, true);
    http.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    var body = 'coordinate=(' + coords.x + ', ' + coords.y + ')';
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
    $('.parking').removeClass('found');
}