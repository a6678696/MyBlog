(function (w, d) {
    w.hljsln = {
        initLineNumbersOnLoad: initLineNumbersOnLoad,
        addLineNumbersForCode: addLineNumbersForCode
    };

    function initLineNumbersOnLoad() {
        if (d.readyState === 'interactive' || d.readyState === 'complete') {
            documentReady();
        } else {
            w.addEventListener('DOMContentLoaded', function () {
                documentReady();
            });
        }
    }

    function addLineNumbersForCode(html) {
        var num = 0;
        var max = 0;
        html = html.replace(/\r\n|\r|\n/g, function (b) {
            max++;
            if (max != 0) {
                return b;
            }
        });
        html = html.replace(/\r\n|\r|\n/g, function (a) {
            num++;
            if (num != 0 && num != max) {
                return a + '<span class="ln-num" data-num="' + num + '"></span>';
            }else if (num != 0 && num == max) {
                return a + ' ';
            }
        });
        html = '<span class="ln-bg" style="margin-left: 1px"></span>' + html;
        return html;
    }

    function documentReady() {
        var elements = d.querySelectorAll('pre code');
        for (var i = 0; i < elements.length; i++) {
            if (elements[i].className.indexOf('hljsln') == -1) {
                var html = elements[i].innerHTML;
                html = addLineNumbersForCode(html);
                elements[i].innerHTML = html;
                elements[i].className += ' hljsln';
            }
        }
    }
}(window, document));