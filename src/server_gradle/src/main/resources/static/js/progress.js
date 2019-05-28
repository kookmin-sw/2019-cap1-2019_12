var i = 1;
$('.progress .circle').removeClass().addClass('circle');
$('.progress .bar').removeClass().addClass('bar');
setInterval(function() {
  $('.progress .circle:nth-of-type(' + i + ')').addClass('active');

  $('.progress .circle:nth-of-type(' + (i-1) + ')').removeClass('active').addClass('done');

  $('.progress .circle:nth-of-type(' + (i-1) + ') .label').html('&#10003;');

  $('.progress .bar:nth-of-type(' + (i-1) + ')').addClass('active');

  $('.progress .bar:nth-of-type(' + (i-2) + ')').removeClass('active').addClass('done');

  i++;

  if (i==0) {
    $('.progress .bar').removeClass().addClass('bar');
    $('.progress div.circle').removeClass().addClass('circle');
    i = 1;
  }
}, 1000);
