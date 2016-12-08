
// 初始化当前菜单
$(function(){
    var $list = $('.menu-nav').find('li');
    $list.removeClass('active');
    $list.each(function(){
        var $li = $(this);
        var href = $li.find('a[href]').attr('href');
        if (href && location.pathname.indexOf(href) == 0) {
            $li.addClass('active');
            return false;
        }
    });
});

// 搜索框动画
$(function(){
    var $searchBox = $('.search-box');
    var $box = $searchBox.find('.search-box-input');
    $box.bind('focus', function(){
        $searchBox.addClass('search-box-focus');
    });
    $box.bind('blur', function(){
        $searchBox.removeClass('search-box-focus');
    });
});

// tab
(function($){
    var Tab = function($el, options){
        this.options = $.extend({}, $.fn.tab.defaults, options || {});
        this.$el = $el;
        this.index = null;
        this._create();
        return this;
    };

    $.extend(Tab.prototype, {
        _create: function () {
            this.$menuList = this.$el.find(this.options.menuSelector);
            if (this.$menuList.length == 0) return;
            this.$contentList = this.$el.find(this.options.contentSelector);
            if (this.$contentList.length == 0) return;
            if (this.$menuList.length != this.$contentList.length) {
                throw 'The number of the "' + this.options.menuSelector + '" and "' + this.options.contentSelector + '" is not match';
            }

            var _this = this;
            this.$el.delegate(this.options.menuSelector, this.options.eventType, function(){
                var $current = $(this);
                var index = _this.$menuList.index($current);
                _this.activeTab(index);
            });

            this.activeTab(0);
        },
        activeTab: function(index) {
            if (this.index == index) return;
            var $menu = $(this.$menuList.get(index));
            var $content = $(this.$contentList.get(index));
            $menu.addClass(this.options.menuActiveClass)
                .siblings(this.options.menuSelector).removeClass(this.options.menuActiveClass);
            $content.show()
                .siblings(this.options.contentSelector).hide();
            if (typeof this.options.change == 'function') {
                this.options.change.apply(this, [index, $menu, $content]);
            }
            this.index = index;
        }
    });

    var old = $.fn.tab;
    $.fn.tab = function (options) {
        return this.each(function () {
            var _this = $(this), data = _this.data('tab');
            if (!data) _this.data('tab', (data = new Tab(_this, options)))
        });
    };
    $.fn.tab.Constructor = Tab;
    $.fn.tab.noConflict = function () {$.fn.tab = old;return this;};
    $.fn.tab.defaults = {
        eventType: 'click',
        menuSelector: '.tab-menu',
        contentSelector: '.tab-content',
        menuActiveClass: 'tab-menu-active',
        change: function(index, $menu, $content){}
    };
})(jQuery);


//轮播
function Slider() {
	this.slider = null;
	this.ul = null;
	this.ol = null;
	this.index = 1;
	this.timer = null;

	this.init();
}
Slider.prototype = {
	init: function(){
		var _this = this;
		this.slider = document.querySelector('#slider');
		this.ul = this.slider.querySelector('ul');
		this.ol = this.slider.querySelector('ol');

		this.ul.addEventListener("mouseover",function(){
			_this.pause();
		}, false);
		this.ul.addEventListener("mouseout",function(){
			_this.start();
		}, false);

		this.controlList = this.ol.querySelectorAll('li');
		for (var i = 0; i < this.controlList.length; i++) {
			this.controlList[i].setAttribute('data-index', i + 1);
			this.controlList[i].addEventListener("mouseover", function(){
				_this.goto(parseInt(this.getAttribute('data-index')));
			});
		}
	},
	start: function(){
		console.log('start slider');
		var _this = this;
		this.timer = setInterval(function(){
			_this.next();
		}, 3000);
		return this;
	},
	pause: function(){
		console.log('pause slider');
		clearInterval(this.timer);
	},
	goto: function(index) {
		this.index = index;
		this.move();
	},
	prev: function() {
		this.index --;
		if (this.index < 1) {
			this.index = 5;
		}
		this.move();
	},
	next: function() {
		this.index ++;
		if (this.index > 5) {
			this.index = 1;
		}
		this.move();
	},
	move: function() {
		var nextPos = (this.index - 1) * -1000;
		console.log('move to: ' + this.index)
		this.ul.style.marginLeft = nextPos + 'px';
		this.refresh();
	},
	refresh: function() {
		for (var i = 0; i < this.controlList.length; i++) {
			this.controlList[i].className = (i+1 == this.index) ? 'active' : '';
		}
	}

};

function isEmpty(text) {
    return !text || null == text || "" == text || "null" == text || "undefined" == text;
}

function forward(url) {
    location.href = url;
}

function reload() {
    location.reload();
}

function logout() {
    $.ajax({
        url: '/logout',
        success: function(){
            reload();
        }
    })
}

(function($){
    $.fn.submitWithAjax = function(options) {
        options = $.extend({}, {success: null, error: null}, options || {});
        var $form = $(this);
        $.ajax({
            type: 'post',
            datatype: 'json',
            url: $form.attr('action'),
            data: $form.serialize(),
            success: function (result) {
                if (!result) return;
                if (result.success) {
                    if (typeof options.success == 'function') {
                        options.success.apply(this, arguments);
                    } else {
                        var url = result.redirectUrl;
                        if (!isEmpty(url)) {
                            forward(url);
                        } else {
                            if (alert('操作成功！')) {
                                reload();
                            }
                        }
                    }
                } else {
                    if (typeof options.error == 'function') {
                        options.error.apply(this, [result]);
                    } else {
						alert(result.message);
						if (result.code == -1001) {
                            forward('/login?redirectUrl=' + encodeURIComponent(location.href));
						} else if (result.code == -1002) {
							$('#code').val('');
							refreshCode($('#code_pic'));
						}
                    }
                }
            }
        });
    };
})(jQuery);

function refreshCode(el) {
	$(el).attr({src: '/code?t=' + new Date().getTime()});
}

//反馈
function chooseType() {
	var select = document.querySelector("select");
	if (select.value == 0) {
		document.querySelector("#error1").style.display = "block";
	}
	else {
		document.querySelector("#error1").style.display = "none";
	}
}//选项列表
function advice() {
	textarea = document.querySelector("textarea");
	textarea.innerHTML = "";
	textarea.className += " advice";
}
function checkAdvice() {
	var txt = textarea.value;
	textarea.className = "input01";
	document.querySelector("#error2").style.display = "none";
	document.querySelector("#error3").style.display = "none";
	document.querySelector("#error4").style.display = "none";
	if (txt.length == 0) {
		document.querySelector("#error2").style.display = "block";
	}
	else if (txt.length != 0 && txt.length <= 10) {
		document.querySelector("#error3").style.display = "block";
	}
	else if (txt.length >= 200) {
		document.querySelector("#error4").style.display = "block";
	}
}//文本区域
function inputIn(idName) {
    content = document.getElementById(idName);
	content.className = "advice";
}//获得焦点
function checkinput(idName,error) {
	content.className = "";
	content = content.value.replace(/(^\s+)|(\s+$)/g, "");
	if (idName =="name") {
		if (!content.match(/^[\u4e00-\u9fa5]{2,4}$/)) {
			document.querySelector(error).style.display = "block";
		}
		else {
	    	document.querySelector(error).style.display = "none";
		}
	}//姓名
	else if(idName == "advice_phone") {
		var email = document.querySelector("#advice_email");
		if (email.value) {
			document.querySelector(error).style.display = "none";
		}
		else if (!content.match(/^(13|14|15|17|18)+\d{9}$/)) {
			document.querySelector(error).style.display = "block";
		}
		
		else {
	    	document.querySelector(error).style.display = "none";
		}
	}//电话
	else if (idName == "advice_email") {
		var phone = document.querySelector("#advice_phone");
		if (phone.value) {
			document.querySelector(error).style.display = "none";
		}
		else if (!content.match(/^\w{5,15}@\w{2,3}\.\w{2,3}$/)) {
			document.querySelector(error).style.display = "block";
		}
		else {
			document.querySelector(error).style.display = "none";
		}
	}//邮件
	else if (idName = "checkcode") {
		if (!content.match(/^\d{4}$/)) {
			document.querySelector(error).style.display = "block";
		}
	    else {
	    	document.querySelector(error).style.display = "none";
	    }
	}//验证码
}