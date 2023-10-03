jQuery(document).ready(function($) {
    arr = [
        {
            id:0,
            name:"Зачётная неделя",
            creator:"Александр Владимирович Железняк",
            desc: "Лалала",
            count:132,
            id_creator: 5
        },
        {
            id:1,
            name:"Сдача лабораторной",
            creator:"Анастасия Викторовна Купова",
            desc: "Лалала",
            count:10,
            id_creator: 5
        }
    ]
    arr2 = [
        {
            id:0,
            name:"Деканат",
            creator:"Холод Иван Иванович",
            desc: "Очередь на отчисление",
            count: 280,
            id_creator: 5
        },
        {
            id:1,
            name:"Мед пункт",
            creator:"Пупкина Матрёна Всеволодовна",
            desc: "Получить справку об шизофрении",
            count: 78,
            id_creator: 5
        }
    ]
    function setItems(array, b) {
        if (b) {
            array.forEach(function (elem) {
                $('<div class="queueBlock1">' +
                    '<div class="nameQueue">\n' +
                    elem["name"] +
                    '</div>\n' +
                    '<div class="nameTeacher">\n' +
                    elem["creator"] +
                    '</div>\n' +
                    '<div class="quantityPeople">\n' +
                    elem["count"] + ' человека\n' +
                    '</div>\n' +
                    '</div>').appendTo('.listQueue');
            })
        } else {
            array.forEach(function (elem) {
                $('<div class="queueBlock1">' +
                    '<div class="nameQueue">\n' +
                    elem["name"] +
                    '</div>\n' +
                    '<div class="nameTeacher">\n' +
                    elem["creator"] +
                    '</div>\n' +
                    '<div class="quantityPeople">\n' +
                    elem["count"] + ' человека\n' +
                    '</div>\n' +
                    '</div>').appendTo('.listQueue');
            })
        }
    }

    function doTransparent() {
        $('.available').fadeTo(0, 0.3);
    }
    setItems(arr, true)
    doTransparent()
    $('.available').click(function () {
        $('.listQueue').empty();
        $('.myQueue').fadeTo(0, 0.3);
        $(this).fadeTo(0, 1);
        setItems(arr2, false);
    });
    $('.myQueue').click(function () {
        $('.listQueue').empty();
        $('.available').fadeTo(0, 0.3);
        $(this).fadeTo(0, 1);
        setItems(arr,true);
    });
})