$(document).ready(function() {
	/*var navListItems = $('div.setup-panel div a'),
		allWells = $('.setup-content'),
		allNextBtn = $('#btnNext');
		allWells.hide();
	navListItems.click(function(e) {
		e.preventDefault();
		var $target = $($(this).attr('href')),
			$item = $(this);
		if (!$item.hasClass('disabled')) {
			navListItems.removeClass('btn-primary').addClass('btn-default');
			$item.addClass('btn-primary');
			allWells.hide();
			$target.show();
			$target.find('input:eq(0)').focus();
		}
	});
	allNextBtn.click(function() {
		var curStep = $(this).closest(".setup-content"),
			curStepBtn = curStep.attr("id"),
			nextStepWizard = $('div.setup-panel div a[href="#' + curStepBtn + '"]').parent().next().children("a"),
			curInputs = curStep.find("input[type='text'],input[type='url']"),
			isValid = true;
		$(".form-group").removeClass("has-error");
		for (var i = 0; i < curInputs.length; i++) {
			if (!curInputs[i].validity.valid) {
				isValid = false;
				$(curInputs[i]).closest(".form-group").addClass("has-error");
			}
		}
		if (isValid)
			nextStepWizard.removeAttr('disabled').trigger('click');
	});
	$('div.setup-panel div a.btn-primary').trigger('click');
	
	
		$("#btnDisplayGrid").on('click', function(){
			$("#grid-data").bootgrid({
				ajax: true,
				post: function ()
				{
					 To accumulate custom parameter with the request object 
					return {
						id: "b0df282a-0d67-40e5-8558-c9e93b7befed"
					};
				},
				url: "http://tainos.ca/data.json",
				formatters: {
					"link": function(column, row)
					{
						return "<a href=\"#\">" + column.id + ": " + row.sender + "</a>";
					}
				}
			});
		});
	*/
});