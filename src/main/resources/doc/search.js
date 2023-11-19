let api = [];
const apiDocListSize = 4
api.push({
    name: 'default',
    order: '1',
    list: []
})
api[0].list.push({
    alias: 'ArticleTagController',
    order: '1',
    link: '&lt;p&gt;  _前端控制器  &lt;/p&gt;',
    desc: '&lt;p&gt;   前端控制器  &lt;/p&gt;',
    list: []
})
api[0].list.push({
    alias: 'FollowDetailController',
    order: '2',
    link: '&lt;p&gt;  _前端控制器  &lt;/p&gt;',
    desc: '&lt;p&gt;   前端控制器  &lt;/p&gt;',
    list: []
})
api[0].list.push({
    alias: 'RelArticleTagController',
    order: '3',
    link: '&lt;p&gt;  _前端控制器  &lt;/p&gt;',
    desc: '&lt;p&gt;   前端控制器  &lt;/p&gt;',
    list: []
})
api[0].list.push({
    alias: 'RelUserRoleController',
    order: '4',
    link: '&lt;p&gt;  _前端控制器  &lt;/p&gt;',
    desc: '&lt;p&gt;   前端控制器  &lt;/p&gt;',
    list: []
})
api[0].list.push({
    alias: 'RoleController',
    order: '5',
    link: '&lt;p&gt;  _前端控制器  &lt;/p&gt;',
    desc: '&lt;p&gt;   前端控制器  &lt;/p&gt;',
    list: []
})
api[0].list.push({
    alias: 'TagController',
    order: '6',
    link: '&lt;p&gt;  _前端控制器  &lt;/p&gt;',
    desc: '&lt;p&gt;   前端控制器  &lt;/p&gt;',
    list: []
})
api[0].list.push({
    alias: 'TagDetailController',
    order: '7',
    link: '&lt;p&gt;  _前端控制器  &lt;/p&gt;',
    desc: '&lt;p&gt;   前端控制器  &lt;/p&gt;',
    list: []
})
api.push({
    name: '测试分组',
    order: '2',
    list: []
})
api[1].list.push({
    alias: 'UserController',
    order: '1',
    link: '用户接口',
    desc: '用户接口',
    list: []
})
api[1].list[0].list.push({
    order: '1',
    deprecated: 'false',
    url: 'http://localhost:8081/user/login',
    desc: '用户根据账号密码登录',
});
api[1].list[0].list.push({
    order: '2',
    deprecated: 'false',
    url: 'http://localhost:8081/user/getInfo',
    desc: '用户根据token登录',
});
api[1].list[0].list.push({
    order: '3',
    deprecated: 'false',
    url: 'http://localhost:8081/user/register',
    desc: '用户注册',
});
api[1].list[0].list.push({
    order: '4',
    deprecated: 'false',
    url: 'http://localhost:8081/user/validRegisterAccount',
    desc: '检验账号是否已经注册',
});
api[1].list[0].list.push({
    order: '5',
    deprecated: 'false',
    url: 'http://localhost:8081/user/validRegisterMail',
    desc: '检验邮箱是否已经注册',
});
api[1].list[0].list.push({
    order: '6',
    deprecated: 'false',
    url: 'http://localhost:8081/user/getMailCode',
    desc: '登录时获取邮箱验证码',
});
api[1].list[0].list.push({
    order: '7',
    deprecated: 'false',
    url: 'http://localhost:8081/user/validMailCode',
    desc: '检验邮箱验证码是否正确',
});
api.push({
    name: '错误码列表',
    order: '3',
    list: []
})
api.push({
    name: '数据字典',
    order: '3',
    list: []
})
api[3].list.push({
    alias: 'http状态码字典',
    order: '1',
    link: 'http状态码字典',
    desc: 'http状态码字典',
    list: []
})
document.onkeydown = keyDownSearch;
function keyDownSearch(e) {
    const theEvent = e;
    const code = theEvent.keyCode || theEvent.which || theEvent.charCode;
    if (code === 13) {
        const search = document.getElementById('search');
        const searchValue = search.value.toLocaleLowerCase();

        let searchGroup = [];
        for (let i = 0; i < api.length; i++) {

            let apiGroup = api[i];

            let searchArr = [];
            for (let i = 0; i < apiGroup.list.length; i++) {
                let apiData = apiGroup.list[i];
                const desc = apiData.desc;
                if (desc.toLocaleLowerCase().indexOf(searchValue) > -1) {
                    searchArr.push({
                        order: apiData.order,
                        desc: apiData.desc,
                        link: apiData.link,
                        list: apiData.list
                    });
                } else {
                    let methodList = apiData.list || [];
                    let methodListTemp = [];
                    for (let j = 0; j < methodList.length; j++) {
                        const methodData = methodList[j];
                        const methodDesc = methodData.desc;
                        if (methodDesc.toLocaleLowerCase().indexOf(searchValue) > -1) {
                            methodListTemp.push(methodData);
                            break;
                        }
                    }
                    if (methodListTemp.length > 0) {
                        const data = {
                            order: apiData.order,
                            desc: apiData.desc,
                            link: apiData.link,
                            list: methodListTemp
                        };
                        searchArr.push(data);
                    }
                }
            }
            if (apiGroup.name.toLocaleLowerCase().indexOf(searchValue) > -1) {
                searchGroup.push({
                    name: apiGroup.name,
                    order: apiGroup.order,
                    list: searchArr
                });
                continue;
            }
            if (searchArr.length === 0) {
                continue;
            }
            searchGroup.push({
                name: apiGroup.name,
                order: apiGroup.order,
                list: searchArr
            });
        }
        let html;
        if (searchValue === '') {
            const liClass = "";
            const display = "display: none";
            html = buildAccordion(api,liClass,display);
            document.getElementById('accordion').innerHTML = html;
        } else {
            const liClass = "open";
            const display = "display: block";
            html = buildAccordion(searchGroup,liClass,display);
            document.getElementById('accordion').innerHTML = html;
        }
        const Accordion = function (el, multiple) {
            this.el = el || {};
            this.multiple = multiple || false;
            const links = this.el.find('.dd');
            links.on('click', {el: this.el, multiple: this.multiple}, this.dropdown);
        };
        Accordion.prototype.dropdown = function (e) {
            const $el = e.data.el;
            let $this = $(this), $next = $this.next();
            $next.slideToggle();
            $this.parent().toggleClass('open');
            if (!e.data.multiple) {
                $el.find('.submenu').not($next).slideUp("20").parent().removeClass('open');
            }
        };
        new Accordion($('#accordion'), false);
    }
}

function buildAccordion(apiGroups, liClass, display) {
    let html = "";
    if (apiGroups.length > 0) {
        if (apiDocListSize === 1) {
            let apiData = apiGroups[0].list;
            let order = apiGroups[0].order;
            for (let j = 0; j < apiData.length; j++) {
                html += '<li class="'+liClass+'">';
                html += '<a class="dd" href="#_'+order+'_'+apiData[j].order+'_' + apiData[j].link + '">' + apiData[j].order + '.&nbsp;' + apiData[j].desc + '</a>';
                html += '<ul class="sectlevel2" style="'+display+'">';
                let doc = apiData[j].list;
                for (let m = 0; m < doc.length; m++) {
                    let spanString;
                    if (doc[m].deprecated === 'true') {
                        spanString='<span class="line-through">';
                    } else {
                        spanString='<span>';
                    }
                    html += '<li><a href="#_'+order+'_' + apiData[j].order + '_' + doc[m].order + '_' + doc[m].desc + '">' + apiData[j].order + '.' + doc[m].order + '.&nbsp;' + spanString + doc[m].desc + '<span></a> </li>';
                }
                html += '</ul>';
                html += '</li>';
            }
        } else {
            for (let i = 0; i < apiGroups.length; i++) {
                let apiGroup = apiGroups[i];
                html += '<li class="'+liClass+'">';
                html += '<a class="dd" href="#_'+apiGroup.order+'_' + apiGroup.name + '">' + apiGroup.order + '.&nbsp;' + apiGroup.name + '</a>';
                html += '<ul class="sectlevel1">';

                let apiData = apiGroup.list;
                for (let j = 0; j < apiData.length; j++) {
                    html += '<li class="'+liClass+'">';
                    html += '<a class="dd" href="#_'+apiGroup.order+'_'+ apiData[j].order + '_'+ apiData[j].link + '">' +apiGroup.order+'.'+ apiData[j].order + '.&nbsp;' + apiData[j].desc + '</a>';
                    html += '<ul class="sectlevel2" style="'+display+'">';
                    let doc = apiData[j].list;
                    for (let m = 0; m < doc.length; m++) {
                       let spanString;
                       if (doc[m].deprecated === 'true') {
                           spanString='<span class="line-through">';
                       } else {
                           spanString='<span>';
                       }
                       html += '<li><a href="#_'+apiGroup.order+'_' + apiData[j].order + '_' + doc[m].order + '_' + doc[m].desc + '">'+apiGroup.order+'.' + apiData[j].order + '.' + doc[m].order + '.&nbsp;' + spanString + doc[m].desc + '<span></a> </li>';
                   }
                    html += '</ul>';
                    html += '</li>';
                }

                html += '</ul>';
                html += '</li>';
            }
        }
    }
    return html;
}