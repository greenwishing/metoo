(function($){
    $.metoo = $.metoo || {
            version: '0.0.1'
        };
    $.noop = $.noop || function () {};
})(jQuery);
// menu control
(function($){
	$.fn.menuControl = function() {
		var $list = $(this).find('li');
		$list.removeClass('active');
		$list.each(function(){
			var $li = $(this);
			var href = $li.find('a[href]').attr('href');
			if (href && location.pathname.indexOf(href) == 0) {
				$li.addClass('active');
				return false;
			}
		});
	}
})(jQuery);

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

/**
 * 弹出窗口
 * $.metoo.dialog({title: '窗口标题', content: '窗口内容'});
 */
(function($){
    $.metoo.dialog = function(options) {
        options = $.extend({
            title: '标题',
            content: null,
            url: null,
            className: '',
            buttons: []
        }, options || {});
        var $dialog = $('<div class="modal">\n' +
            '<div class="modal-backdrop"></div>\n' +
            '<div class="modal-dialog">\n' +
            '<div class="modal-content">\n' +
            '<div class="modal-header">\n<button type="button" class="close"><span>×</span></button>\n<h4 class="modal-title"></h4>\n</div>\n' +
            '<div class="modal-body"></div>\n' +
            '</div>\n' +
            '</div>\n' +
            '</div>');
        $dialog.data({url: options.url});
        $dialog.find('.modal-title').html(options.title);
        if (options.className) {
            $dialog.addClass(options.className)
        }
        if (options.buttons.length) {
            var $footer = $('<div class="modal-footer"></div>');
            var buttons = options.buttons.map(function (button) {
                return '<button type="button" class="btn ' + button.type + '">' + button.label + '</button>';
            }).join('\n');
            $footer.append(buttons);
            $dialog.find('.modal-content').append($footer);
            $dialog.on('click', '.btn', function () {
                var button = options.buttons[$(this).index()];
                var cb = button.onClick || $.noop;
                cb.call();
                $.metoo.closeDialog($dialog);
            });
        }
        if (options.content) {
            $dialog.find('.modal-body').html(options.content);
        } else if (options.url) {
            $.get(options.url, function(result){
                $dialog.find('.modal-body').html(result);
            });
        }
        $('body').append($dialog).addClass('modal-open');
        $dialog.on('click', '.close', function(){
            $.metoo.closeDialog($dialog);
        });
        if (typeof $dialog.fadeIn === 'function') {
            $dialog.fadeIn('fast');
        } else {
            $dialog.show('fast');
        }
    };

    $.metoo.closeDialog = function($dialog) {
        if (!($dialog instanceof jQuery)) {
            $dialog = $($dialog);
        }
        if (!$dialog.is('.modal')) {
            $dialog = $dialog.closest('.modal');
        }
        if ($dialog) {
            $dialog.off('click');
            if (typeof $dialog.fadeOut === 'function') {
                $dialog.fadeOut('fast', function () {
                    _remove();
                });
            } else {
                _remove();
            }
        }
        function _remove() {
            $dialog.remove();
            $dialog = null;
            var $body = $('body');
            if (!$body.find('.modal').length) {
                $body.removeClass('modal-open');
            }
        }
    };

    $.metoo.reloadDialog = function($dialog, options) {
        if (!($dialog instanceof jQuery)) {
            $dialog = $($dialog);
        }
        if (!$dialog.is('.modal')) {
            $dialog = $dialog.closest('.modal');
        }
        options = $.extend({}, {url: $dialog.data('url'), content: null}, options || {});
        if (options.content) {
            $dialog.find('.modal-body').html(options.content);
        } else if (options.url) {
            $.get(options.url, function(result){
                $dialog.find('.modal-body').html(result);
            });
        }
    }
})(jQuery);

/**
 * 提示信息框
 * $.metoo.alert('提示信息！', {
 * ok: function(){
 *   // ok callback
 * }})
 * 或
 * $.metoo.alert('提示信息！', function(){
 *   // ok callback
 * })
 */
(function($){
    $.metoo.alert = function(message, options) {
        if (typeof options == "function") {
            options = {ok: options};
        }
        options = $.extend({}, {ok: $.noop}, options || {});
        $.metoo.dialog({title: '提示', content: message || '提示信息', buttons: [{
            label: '确定',
            type: 'btn-primary',
            onClick: options.ok
        }]})
    }
})(jQuery);

/**
 * 确认对话框
 * $.metoo.confirm('确定要这样做吗？', {
 * ok: function(){
 *   // ok callback
 * },
 * cancel: function(){
 *   // cancel callback
 * }
 * })
 */
(function($){
    $.metoo.confirm = function(message, options) {
        options = $.extend({}, {ok: $.noop, cancel: $.noop}, options || {});
        $.metoo.dialog({title: '提示', content: message || '提示信息', buttons: [{
            label: '确定',
            type: 'btn-primary',
            onClick: options.ok
        }, {
            label: '取消',
            type: 'btn-default',
            onClick: options.cancel
        }]})
    }
})(jQuery);

// 异步请求
(function($){
    $.metoo.ajax = function(options) {
        options = $.extend({}, options || {});
        $.ajax(options);
    }
})(jQuery);


/**
 * 通用修改状态函数
 */
(function($){
    $.fn.toggleStatus = function(options) {
        options = $.extend({}, {action: 'toggle', scope: 'page'}, options || {});
        var $el = $(this);
        var id = $el.attr('data-id');
        $.metoo.ajax({
            url: options.action + '?id=' + id,
            success: function(result) {
                if (result.success) {
                    if (options.scope == 'page') {
                        reload();
                    } else if (options.scope == 'dialog') {
                        $.metoo.reloadDialog($el);
                    }
                } else {
                    $.metoo.alert(result.message);
                }
            }
        });
    }
})(jQuery);

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
                            reload();
                        }
                    }
                } else {
                    if (typeof options.error == 'function') {
                        options.error.apply(this, [result]);
                    } else {
                        $.metoo.alert(result.message, function(){
                            if (result.code == -1001) {
                                forward('/login?redirectUrl=' + encodeURIComponent(location.href));
                            } else if (result.code == -1002) {
                                $('#code').val('');
                                refreshCode($('#code_pic'));
                            }
                        });
                    }
                }
            }
        });
    };
})(jQuery);

(function($){
    $.fn.submitForm = function() {
        var $form = $(this);
        var valid = true;
		$form.find(':input[data-required]').each(function(){
            var $filed = $(this);
            var $formGroup = $filed.closest('.form-group');
            var $tips = $formGroup.find('.help-block');
            if (isEmpty($filed.val())) {
                var label = $filed.attr('placeholder') || $formGroup.find('.control-label').html() || '';
                if (!$tips.length) {
                    var $inputGroup = $filed.closest('.input-group');
                    $tips = $('<p class="help-block text-highlight"></p>').insertAfter($inputGroup.length ? $inputGroup : $filed);
                }
                $tips.html('请输入' + label + '！');
                $filed.focus();
                valid = false;
                return false;
            } else {
                if ($tips.length) {
                    $tips.empty().hide();
                }
            }
        });
        return valid;
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