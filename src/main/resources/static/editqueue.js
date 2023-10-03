jQuery(document).ready(function($) {
    arr = [{id:5, number:"2371"},{id:3, number:"2391"},{id:4, number:"2376"},{id:7, number:"2395"},{id:4, number:"2376"},{id:7, number:"2395"}]
    arr.forEach(function (elem) {
                $('<div><span>'+elem['number']+'</span></div>').appendTo(".GroupFields")
            })
})