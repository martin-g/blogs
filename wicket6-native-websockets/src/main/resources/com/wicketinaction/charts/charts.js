google.load("visualization", "1", {
	packages:["corechart"]
});

google.setOnLoadCallback(drawChart);

function drawChart() {

	var options = {
		title: 'Stock prices'
	};

	var rawData = [
		['Year', 'Company 1', 'Company 2'],
		["1999", 0, 0]
	];

	var chart;

	var updateChart = function() {
		var data = google.visualization.arrayToDataTable(rawData);
		if (chart) {
			chart.clearChart();
		} else {
			chart = new google.visualization.LineChart(document.getElementById('chart_div'));
		}
		chart.draw(data, options);
	};

	var updateChartData = function(data) {
		var columnIndex = rawData[0].indexOf(data.field);

		var replaced = false;
		for (var r = 1; r < rawData.length; r++) {
			var oldRecord = rawData[r];
			if (oldRecord[0] === data.year) {
				oldRecord[columnIndex] = data.value;
				replaced = true;
				break;
			}
		}

		if (!replaced) {
			var lastRecord = rawData[rawData.length - 1];
			var newRecord = [data.year, lastRecord[1], lastRecord[2]];
			newRecord[columnIndex] = data.value;
			rawData.push(newRecord);
		}

		if (rawData.length > 20) {
			rawData.splice(1, 1);
		}

		updateChart();
	};

	Wicket.Event.subscribe("/websocket/open", function(jqEvent) {
		updateChart();
	});

	Wicket.Event.subscribe("/websocket/message", function(jqEvent, message) {
		var record = jQuery.parseJSON(message);
		if (record && record.year) {
			updateChartData(record);
		}
	});

}

