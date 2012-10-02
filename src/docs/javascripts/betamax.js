$(document).ready(function() {

	// Google code prettify
	$('pre').addClass('prettyprint');
	prettyPrint();

	// wrap containers around each tab group

	// wrap each tab in a .tab-pane
	$('#maven, #gradle, #grails, #junit, #spock').each(function() {
		$(this).nextUntil('h1, h2, h3').andSelf().wrapAll('<div class="tab-pane"></div>');
	});

	// move ids from tab headings to containing .tab-pane
	$('.tab-pane h3').each(function() {
		var id = $(this).attr('id');
		$(this).removeAttr('id').parent().attr('id', id);
	});

	// wrap all contiguous .tab-pane elements in a .tab-content
	$(':not(.tab-pane) + .tab-pane').each(function() {
		$(this).nextUntil(':not(.tab-pane)').andSelf().wrapAll('<div class="tab-content"></div>');
	});

	// for each tab container, create nav links
	$('.tab-content').each(function() {
		var tabs = $('<ul class="nav nav-tabs"></ul>');
		$(this).children().each(function() {
			var tab = $('<li></li>');
			tab.append($('<a></a>', {
				href: '#' + $(this).attr('id'),
				text: $(this).find('h3').text(),
				click: function(e) {
					e.preventDefault();
					$(this).tab('show');
				}
			}).data('toggle', 'tab'));
			tabs.append(tab);
		});
		tabs.insertBefore(this);
	});

	// activate the first link & tab in each group
	$('.nav-tabs li:first-child a').tab('show');
//	$('.tab-content li:first-child, .tabs li:first-child a').addClass('active');

	// replace h1 with fancier but less SEO-compliant text
	$('h1, nav h2').html('&beta;etamax');

	// make teaser blocks even height
	var forceEvenTeaserHeights = function() {
		var maxTeaserHeight = 0;
		$('.teaser').each(function() {
			maxTeaserHeight = Math.max(maxTeaserHeight, $(this).height());
		});
		$('.teaser').css('minHeight', maxTeaserHeight);
	};
	$(window).resize(forceEvenTeaserHeights);

	// FOUC prevention
	$(window).load(function() {
		$('body').addClass('ready');
		// force mobile URL bar out of view if we're at the top of the page
		if ($(window).scrollTop() == 0) {
			window.scrollTo(0, 1);
		}
	});

});