var helium = {
    callback: undefined, data: {}, init: function () {
        helium.callback = (arguments.length > 0) ? arguments[0] : helium.report;
        if (window.localStorage) {
            helium.load();
            helium.data.timeout = 3000;
            helium.save();
            helium.checkstatus()
        } else {
            throw new Error("localStorage API not found")
        }
    }, checkstatus: function () {
        if (typeof helium.data.status === "undefined") {
            helium.data.status = 0
        }
        if (helium.data.status === 0) {
            var html = ["<h1>Paste a list of pages on your site you want to test</h1>", '<textarea id="cssdetectTextarea"></textarea><input type="button" id="cssdetectStart" value="Start"/>', '<input type="button" id="cssdetectRestart" value="Reset to Beginning"/>'];
            helium.generateCSS();
            var div = document.createElement("div");
            div.id = "cssdetectID";
            div.innerHTML = html.join("");
            helium.$("body")[0].appendChild(div);
            helium.on(helium.$("#cssdetectStart"), "click", function () {
                var list = document.getElementById("cssdetectTextarea").value.split("\n");
                helium.setPageList(list);
                helium.data.status = 1;
                helium.data.findinglist = helium.data.pagelist.slice(0);
                helium.save();
                helium.nav(helium.data.findinglist)
            }, false);
            helium.on(helium.$("#cssdetectRestart"), "click", function () {
                document.getElementById("cssdetectTextarea").value = "";
                helium.reset()
            }, false);
            return false
        }
        if (helium.data.status === 1) {
            helium.findstylesheets();
            return false
        }
        if (helium.data.status === 2) {
            helium.getcss();
            return false
        }
        if (helium.data.status === 3) {
            helium.checkcss();
            return false
        }
        if (helium.data.status === 4) {
            helium.callback()
        }
    }, report: function () {
        var flip = false;
        var html = ["<h1>CSS Detection Report</h1>", '<input type="button" id="cssreportResetID" value="New Test (Warning: This erases all data from the current test)"/>', '<h2> <a id="cssreportDownloadReport" href="" target="_blank"> Download Report </a> </h2>', '<div class="cell" id="cssdetectDesc">', '<div class="green">Green: unmatched selectors</div>', '<div class="black">Black: matched selectors that are defined with non-matched selectors</div>', '<div class="red">Red: a malformed selector. ** Note: not all malformed selectors are bad. Chrome won\'t parse -moz extensions for example.</div>', '<div class="blue">Blue: a selector with a pseudo-class. You must test these manually.</div>', "</div>"];
        var download_report = ["{G} Green: unmatched selectors", "{B} Black: matched selectors that are defined with non-matched selectors", "{R} Red: a malformed selector. ** Note: not all malformed selectors are bad. Chrome won't parse -moz extensions for example.", "{BL} Blue: a selector with a pseudo-class. You must test these manually."];
        for (var i = 0; i < helium.data.stylesheets.length; i++) {
            html.push('<h2><a href="' + helium.data.stylesheets[i].url + '">' + helium.data.stylesheets[i].url + "</a></h2>");
            download_report.push("\r\nStylesheet: " + helium.data.stylesheets[i].url + "\r\n");
            var sels = helium.data.stylesheets[i].selectors;
            if (sels.length > 0) {
                html.push('<ul class="cell">');
                for (var d = 0; d < sels.length; d++) {
                    var tmpstr = [];
                    var counttrue = 0;
                    download_report.push([]);
                    var download_report_length = download_report.length - 1;
                    for (var e = 0; e < sels[d].length; e++) {
                        sels[d][e].s = helium.trim(sels[d][e].s);
                        if (sels[d][e].v === true && sels[d].length > 1) {
                            tmpstr.push('<span class="matched_selector selector">' + sels[d][e].s + "</span>");
                            download_report[download_report_length].push("{B}" + sels[d][e].s);
                            counttrue++
                        }
                        if (sels[d][e].v === "pseudo_class") {
                            tmpstr.push('<span class="pseudo_class selector">' + sels[d][e].s + "</span>");
                            download_report[download_report_length].push("{BL}" + sels[d][e].s)
                        }
                        if (sels[d][e].v === "invalid_selector") {
                            tmpstr.push('<span class="invalid_selector selector">' + sels[d][e].s + "</span>");
                            download_report[download_report_length].push("{R}" + sels[d][e].s)
                        }
                        if (sels[d][e].v === false) {
                            tmpstr.push('<span class="selector">' + sels[d][e].s + "</span>");
                            download_report[download_report_length].push("{G}" + sels[d][e].s)
                        }
                    }
                    if (counttrue === sels[d].length) {
                        tmpstr = []
                    }
                    counttrue = 0;
                    if (tmpstr.length > 0) {
                        var classname;
                        if (flip) {
                            classname = ' class="alternate"';
                            flip = false
                        } else {
                            classname = "";
                            flip = true
                        }
                        html.push("<li" + classname + ">" + tmpstr.join(", ") + "</li>");
                        download_report[download_report_length] = download_report[download_report_length].join(", ")
                    } else {
                        download_report.pop()
                    }
                }
                html.push("</ul>")
            } else {
                html.push('<div class="cell">No unmatched selectors found.</div>');
                download_report.push("No unmatched selectors found.")
            }
        }
        var div = document.createElement("div");
        div.id = "cssdetectID";
        div.innerHTML = html.join("");
        helium.generateCSS();
        helium.$("body")[0].appendChild(div);
        var toggles = helium.$("#cssdetectID ul li");
        for (var s = 0; s < toggles.length; s++) {
            (function () {
                var i = s;
                helium.on(toggles[i], "click", function () {
                    if (this.style.opacity === "0.5") {
                        this.style.opacity = "1"
                    } else {
                        this.style.opacity = "0.5"
                    }
                }, false)
            })()
        }
        helium.on(helium.$("#cssreportResetID"), "click", function () {
            helium.reset()
        }, false);
        if (window.URL.createObjectURL && window.Blob) {
            var blob = new Blob([download_report.join("\r\n")], {type: "text/rtf"});
            var btn = helium.$("#cssreportDownloadReport");
            btn[0].href = window.URL.createObjectURL(blob);
            btn[0].download = "helium-report.txt"
        } else {
            helium.on(helium.$("#cssreportDownloadReport"), "click", function () {
                alert("Report downloads aren't supported in this browser. The necessary file api's aren't available. Use Chrome or Firefox.")
            })
        }
    }, generateCSS: function () {
        var css = ["#cssdetectID{", 'font-family: "Helvetica Neue", Helvetica, Arial, sans-serif;', "font-size: 16px;", "font-weight: bold;", "color: #fff;", "position: absolute;", "z-index: 999999999;", "top: 10%;", "width: 80%;", "left: 10%;", "background-color: #3498db;", "padding: 10px 20px 20px 20px;", "border: none", "}", "#cssdetectID .cell{", "background: #fff;", "padding: 10px;", "color: #000;", "}", "#cssdetectID div{", "border:none;", "}", "#cssdetectID .alternate{", "border: 1px solid #eee;", "border-width: 1px 0 1px 0;", "}", "#cssdetectID ul{", "list-style: none;", "padding: 0;", "margin: 0;", "}", "#cssdetectID li:hover{", "background: #eee", "}", "#cssdetectID h1{", "margin: 0 0 10px 0;", "padding: 0;", "font-size: 20px;", "}", "#cssdetectID h2{", "margin: 0 0 0 0;", "padding: 0;", "font-size: 16px;", "}", "#cssdetectDesc{", "background: #8ac6ed;", "}", "#cssdetectID textarea{", "width: 100%;", "height:300px;", "border: none;", "margin: 0 0 10px 0;", "padding: 10px;", "resize: none;", "outline: 1px none transparent;", "border-radius: 0;", "}", "#cssdetectID input{", "background: #fff !important;", "color: #000 !important;", "border: none;", "padding: 10px 20px 10px 20px;", "margin: 0 10px 0 0;", "font-size: 18px;", "cursor: pointer;", "-webkit-appearance: button;", "}", "#cssdetectID input:hover{", "background: #ecf0f1;", "pointer: cursor;", "}", "#cssdetectID a{", "color: #fff", "}", "#cssreportResetID{", "position:absolute;", "top: 10px;", "right: 10px;", "}", "#cssdetectID .green, #cssdetectID .selector{", "color: #009000;", "}", "#cssdetectID .black, #cssdetectID .matched_selector{", "color: #000000;", "}", "#cssdetectID .red, #cssdetectID .invalid_selector{", "color: #cc0000;", "}", "#cssdetectID .blue, #cssdetectID .pseudo_class{", "color: #0000cc", "}"];
        var style = document.createElement("style");
        style.innerHTML = css.join("");
        helium.$("body")[0].appendChild(style)
    }, reset: function () {
        localStorage.removeItem("cssdetect");
        var nodes = helium.$("#cssdetectID");
        for (var i = 0; i < nodes.length; i++) {
            nodes[i].parentNode.removeChild(nodes[i])
        }
        helium.init()
    }, checkcss: function () {
        setTimeout(function () {
            for (var i = 0; i < helium.data.pages.length; i++) {
                if (helium.data.pages[i].url === helium.data.currenturl) {
                    for (var b = 0; b < helium.data.pages[i].links.length; b++) {
                        var thislink = helium.data.pages[i].links[b];
                        for (var c = 0; c < helium.data.stylesheets.length; c++) {
                            if (helium.data.stylesheets[c].url === thislink) {
                                var stylesheet = helium.data.stylesheets[c];
                                for (var d = 0; d < stylesheet.selectors.length; d++) {
                                    for (var e = 0; e < stylesheet.selectors[d].length; e++) {
                                        var response = helium.find(stylesheet.selectors[d][e].s);
                                        if (response === true) {
                                            stylesheet.selectors[d][e].v = true
                                        }
                                        if (response === "invalid_selector") {
                                            stylesheet.selectors[d][e].v = "invalid_selector"
                                        }
                                        if (response === "pseudo_class") {
                                            stylesheet.selectors[d][e].v = "pseudo_class"
                                        }
                                    }
                                }
                                c = helium.data.stylesheets.length
                            }
                        }
                    }
                    i = helium.data.pages.length
                }
            }
            helium.save();
            if (helium.data.pagelist.length > 0) {
                helium.nav()
            } else {
                helium.data.status = 4;
                helium.save();
                helium.callback()
            }
        }, helium.data.timeout)
    }, find: function (selector) {
        try {
            if (helium.$(selector).length > 0) {
                return true
            }
        } catch (err) {
            return "invalid_selector"
        }
        var parse = selector.match(/\:+[\w-]+/gi);
        if (parse !== null && parse.hasOwnProperty("length") && parse.length > 0) {
            return "pseudo_class"
        } else {
            return false
        }
    }, findstylesheets: function () {
        var page = {url: helium.data.currenturl, links: []};
        var links = helium.$('link[rel="stylesheet"]');
        for (var i = 0; i < links.length; i++) {
            var tmplink = links[i].getAttribute("href");
            if (tmplink.indexOf("http") !== 0 && tmplink.substr(0, 2) !== "//") {
                if (tmplink.indexOf("/") !== 0) {
                    var lastDir = window.location.pathname.lastIndexOf("/");
                    var directory;
                    if (lastDir > 0) {
                        directory = window.location.pathname.substring(0, lastDir + 1)
                    } else {
                        directory = "/"
                    }
                    tmplink = directory + tmplink
                }
                tmplink = window.location.protocol + "//" + window.location.hostname + ":" + window.location.port + tmplink
            }
            if (tmplink.indexOf(window.location.hostname) !== -1) {
                page.links.push(tmplink);
                if (typeof helium.data.stylesheets === "undefined") {
                    helium.data.stylesheets = []
                }
                helium.data.stylesheets.push(tmplink)
            }
        }
        if (typeof helium.data.pages === "undefined") {
            helium.data.pages = []
        }
        helium.data.pages.push(page);
        helium.save();
        if (helium.data.findinglist.length > 0) {
            helium.nav(helium.data.findinglist)
        } else {
            helium.data.stylesheets.sort();
            for (var i = 0; i < helium.data.stylesheets.length - 1; i++) {
                if (helium.data.stylesheets[i] === helium.data.stylesheets[i + 1]) {
                    helium.data.stylesheets.splice(i--, 1)
                }
            }
            for (var i = 0; i < helium.data.stylesheets.length; i++) {
                var tmp = helium.data.stylesheets[i];
                helium.data.stylesheets[i] = {url: tmp, selectors: []}
            }
            helium.data.status = 2;
            helium.save();
            helium.checkstatus()
        }
    }, getcss: function () {
        for (var i = 0; i < helium.data.stylesheets.length; i++) {
            helium.get(helium.data.stylesheets[i].url, i, function (index, data) {
                data = data.replace(/\/\*[\s\S]*?\*\//gim, "");
                var selectors = data.replace(/\n/g, "").match(/[^\}]+[\.\#\-\w]?(?=\{)/gim) || [];
                var results = [];
                for (var e = 0; e < selectors.length; e++) {
                    var sel = selectors[e].split(",");
                    if (sel.length > 1) {
                        var arr = [];
                        for (var t = 0; t < sel.length; t++) {
                            if (sel[t] !== "") {
                                arr.push({s: sel[t], v: false})
                            }
                        }
                        results.push(arr)
                    } else {
                        results.push([{s: selectors[e], v: false}])
                    }
                }
                helium.data.stylesheets[index].selectors = results;
                helium.save();
                if (index === helium.data.stylesheets.length - 1) {
                    helium.data.status = 3;
                    helium.nav()
                }
            })
        }
    }, nav: function (list) {
        if (!list) {
            list = helium.data.pagelist
        }
        var next = list.shift();
        if (next !== undefined) {
            helium.data.currenturl = next
        }
        helium.save();
        if (next !== undefined) {
            window.location = next
        }
    }, setPageList: function (pagelist) {
        helium.data.pagelist = pagelist;
        helium.save()
    }, trim: function (str) {
        if (typeof String.prototype.trim === "function") {
            return str.trim()
        } else {
            return str.replace(/^\s+/, "").replace(/\s+$/, "")
        }
    }, on: function (target, ev, fn) {
        target = target[0] || target;
        target.addEventListener(ev, fn, false)
    }, load: function () {
        if (!localStorage.cssdetect) {
            localStorage.cssdetect = JSON.stringify({})
        }
        helium.data = JSON.parse(localStorage.cssdetect)
    }, save: function () {
        localStorage.cssdetect = JSON.stringify(helium.data)
    }, clear: function () {
        delete localStorage["cssdetect"]
    }, get: function (url, index, callback) {
        var http;
        if (window.attachEvent) {
            http = new ActiveXObject("Microsoft.XMLHTTP")
        } else {
            http = new XMLHttpRequest()
        }
        http.open("GET", url);
        http.onreadystatechange = function () {
            if (http.readyState === 4) {
                callback(index, http.responseText)
            }
        };
        http.send(null)
    }, $: function (selector) {
        return document.querySelectorAll(selector)
    }
};