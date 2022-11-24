let form = document.getElementById('discover-form');

form.addEventListener('submit', event => {
	
	let swLat = form.elements['swLat']
	let swLatValue = Number.parseFloat(swLat.value).toFixed(2);
	let latError = document.getElementById('lat-error');
	let error = false;
	if(Number.isNaN(swLatValue) || swLatValue < -90 || swLatValue > 90){
		latError.textContent = 'Invalid entry, must be in range -90 to + 90';
		latError.style.color = 'red';
		swLat.focus();
		error = true;
	} else {
		latError.textContent = ''
		error = false;
	}
	
	let swLong = form.elements['swLong']
	let swLongValue = Number.parseFloat(swLong.value).toFixed(2);
	let longError = document.getElementById('long-error');
	if(Number.isNaN(swLongValue) || swLongValue < -180 || swLongValue > 180){
		longError.textContent = 'Invalid entry, must be in range -180 to + 180';
		longError.style.color = 'red';
		swLong.focus();
		error = true;
	} else {
		longError.textContent = '';
		error = error || false;
	}
	
	let size = form.elements['size']
	let sizeValue = Number.parseInt(size.value);
	
	let sizeError = document.getElementById('size-error');
	if(Number.isNaN(sizeValue) || size > 0 || size < 6){
		sizeError.textContent = 'Invalid entry, must be in range 1 to 5';
		sizeError.style.color = 'red';
		size.focus();
		error = true;
	} else {
		sizeError.textContent = '';
		error = error || false;
	}
	
	if(!error){
	    discover_segments(swLatValue, swLongValue, sizeValue);
	}
	event.preventDefault();
	});

let segment_rows = '';
let data = []
const discover_output_element = document.getElementById('discover_output');
function buildRows(item){
	segment_rows +='<tbody>';
	segment_rows +='<tr>';
	segment_rows +='<td>'+item.name+'</td>';
	segment_rows +='<td>'+item.qomWatts+'</td>';
	segment_rows +='<td>'+item.komWatts+'</td>';
	segment_rows +='<td>'+item.distance+'</td>';
	segment_rows +='<td>'+item.average_grade+'</td>';
	segment_rows +='<td>'+item.maximum_grade+'</td>';
	segment_rows +='<td>'+item.elevation_low+'</td>';
	segment_rows +='<td>'+item.elevation_high+'</td>';
	segment_rows +='<td>'+item.qom+'</td>';
	segment_rows +='<td>'+item.kom+'</td>';
	segment_rows +='<td>'+item.athlete_count+'</td>';
	segment_rows +='<td>'+item.effort_count+'</td>';
	segment_rows +='<td>'+item.climb_category+'</td>';
	segment_rows +='<td>'+item.city+'</td>';
	segment_rows +='<td>'+item.state+'</td>';
	segment_rows +='<td>'+item.country+'</td>';
	segment_rows +='</tr>';
	segment_rows +='</tbody>';
}

const tableButtons = document.querySelectorAll("th button");
[...tableButtons].map((button) => {
    button.addEventListener("click", (e) => {
      resetButtons(e);
      if (e.target.getAttribute("data-dir") == "desc") {
        sortData(data, e.target.id, "desc");
        e.target.setAttribute("data-dir", "asc");
      } else {
        sortData(data, e.target.id, "asc");
        e.target.setAttribute("data-dir", "desc");
        }
      });
      }
      )
      
const sortData = (data, param, direction = "asc") => {
	segment_rows = '';
	console.log(direction + " " + param)
	const sortedData =
    direction == "asc"
      ? [...data].sort(function (a, b) {
          if (a[param] < b[param]) {
            return -1;
          }
          if (a[param] > b[param]) {
            return 1;
          }
          return 0;
        })
      : [...data].sort(function (a, b) {
          if (b[param] < a[param]) {
            return -1;
          }
          if (b[param] > a[param]) {
            return 1;
          }
          return 0;
        });
        sortedData.forEach(buildRows)
        $('#segments  tbody').remove();
        $('#segments').append(segment_rows);
}
      
const resetButtons = (event) => {
  [...tableButtons].map((button) => {
    if (button !== event.target) {
      button.removeAttribute("data-dir");
    }
  });
};

export function discover_segments(swLat, swLong, size){
	segment_rows = '';
	let promise = $.get("http://localhost:8080/api/v1/grid/discover?swLat="+swLat+"&swLong="+swLong+"&size="+size);
	promise.then(function(response) { 
	response.forEach(buildRows)
	data = response;
	
	$('#segments  tbody').remove();
     $('#segments').append(segment_rows);
	},
	error => discover_output_element.innerHTML = "ERROR: "+JSON.stringify(error));
	
}
