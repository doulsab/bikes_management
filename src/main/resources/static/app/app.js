let myApp = angular.module('myApp', []);

myApp.factory('BikeTypesFactory', () => {
	let bikeTypes = [
		'Adventure',
		'Hybrid Bike',
		'Mountain Bike',
		'Road Bike',
		'Cruiser Bike',
		'BMX Bike',
		'Folding Bike',
		'Touring Bike',
		'Electric Bike',
		'Gravel Bike',
		'City Bike',
		'Scooter',
		'Dual-Sport'
	];

	return {
		getBikeTypes: () => {
			return bikeTypes;
		}
	};
});

// SweetAlert Service
myApp.service('meassgeAlertService', ['$q', function($q) {
	this.showAlert = function(title, text, icon) {
		Swal.fire({
			title: title,
			text: text,
			icon: icon,
			confirmButtonText: 'OK'
		});
	};
}]);


myApp.factory('tokenAuth', () => {
	// Function to include the token in subsequent requests
	let token = sessionStorage.getItem('authToken');
	return {
		getToken: () => {
			return {
				'Authorization': token ? 'Bearer ' + token : ''
			};
		}
	};

});

myApp.service('msgOkayService', ['$q', function($q) {
	this.showAlert = function(title, text, icon) {
		let deferred = $q.defer();

		Swal.fire({
			title: title,
			text: text,
			icon: icon,
			confirmButtonText: 'OK'
		}).then((result) => {
			if (result.isConfirmed) {
				deferred.resolve(result);
			} else {
				deferred.reject(result);
			}
		});

		return deferred.promise;
	};
}]);
