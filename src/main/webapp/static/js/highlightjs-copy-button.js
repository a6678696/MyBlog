/*! highlightjs-copy-button v1.0.5 */
(function (w) {
    'use strict';

    var BLOCK_NAME = 'hljs-button',
        LN_CLASS = 'hljs-ln-code',
        TEXT_COPY = 'Copy',
        TEXT_ERROR = 'Error',
        TEXT_COPIED = 'Copied';

    // https://wcoder.github.io/notes/string-format-for-string-formating-in-javascript
    String.prototype.format = String.prototype.f = function () {
        var args = arguments;
        return this.replace(/\{(\d+)\}/g, function (m, n) {
            return args[n] ? args[n] : m;
        });
    };

    if (typeof w.hljs === 'undefined') {
        console.error('highlight.js not detected!');
    } else {
        w.hljs.initCopyButtonOnLoad = onLoad;
        w.hljs.addCopyButton = addCopyButton;
        w.hljs.copyCode = copyCode;

        addStyles();
    }

    function copyCode(event) {
        var target = event.target || event.srcElement;
        if (target.className === BLOCK_NAME) {
            event.preventDefault();

            var el = document.getElementById('post-id-target');
            if (!el) {
                el = document.createElement("textarea");
                el.style.position = "absolute";
                el.style.left = "-9999px";
                el.style.top = "0";
                el.id = 'hljs-copy-el';
                document.body.appendChild(el);
            }
            el.textContent = event.currentTarget.innerText;
            el.select();

            try {
                var successful = document.execCommand('copy');
                target.dataset.title = successful ? TEXT_COPIED : TEXT_ERROR;
                if (successful) {
                    setTimeout(function () {
                        target.dataset.title = TEXT_COPY;
                    }, 2000);
                }
            } catch (err) {
                target.dataset.title = TEXT_ERROR;
            }
        }
    }

    function addStyles() {
        var css = document.createElement('style');
        css.type = 'text/css';
        css.innerHTML = ([
            '.hljs{position: relative}',
            '.hljs:hover .{0}{display: block}',
            '.{0}{',
            'display: none;',
            'position: absolute;',
            'right: 0;',
            'top: 0;',
            'background-color: white;',
            'padding: 2px 10px;',
            'margin: 3px;',
            'border-radius: 5px;',
            'border: 1px solid darkgray;',
            'cursor: pointer;',
            'box-shadow: 0 1px 1px rgba(0,0,0,0.12), 0 1px 1px rgba(0,0,0,0.24);',
            '}',
            '.{0}:after{',
            'content: attr(data-title)',
            '}'
        ].join('')).format(BLOCK_NAME);
        document.getElementsByTagName('head')[0].appendChild(css);
    }

    function onLoad() {
        if (document.readyState === 'complete') {
            documentReady();
        } else {
            w.addEventListener('DOMContentLoaded', documentReady);
        }
    }

    function documentReady() {
        try {
            var blocks = document.querySelectorAll('code.hljs');

            for (var i in blocks) {
                if (blocks.hasOwnProperty(i)) {
                    addCopyButton(blocks[i]);
                }
            }
        } catch (e) {
            console.error('CopyButton error: ', e);
        }
    }

    function addCopyButton(element) {
        if (typeof element !== 'object') {
            return;
        }

        element.innerHTML = element.innerHTML + ('<div class="{0}" data-title="{1}"></div>').format(BLOCK_NAME, TEXT_COPY);
        element.setAttribute('onclick', "hljs.copyCode(event)");
    }

}(window));
