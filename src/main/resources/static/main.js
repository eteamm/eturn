jQuery(document).ready(function($) {
    $('.exitBtn').click(function () {
        arr = [{id:5, text:"hahaev1"},{id:3, text:"hahaev2"}]
        arr.forEach(function (elem) {
            $('.exitBtn').text(elem.text)
        })
    })

})