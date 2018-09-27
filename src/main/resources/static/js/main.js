var rolesApi = Vue.resource('api/roles{/id}')

Vue.component('role-form', {
    props: ['roles'],
    data: function() {
        return {
            text: ''
        }
    },
    template:
        '<div>' +
            '<input type="text" placeholder="add role" v-model="text" />' +
            '<input type="button" value="Save" @click="save" />' +
        '</div>',
    methods: {
        save: function() {
            var role = { role: this.text };

            rolesApi.save({}, role).then(result =>
                result.json().then(data => {
                    this.roles.push(data);
                })
            )
        }
    }
});

Vue.component('roles-row', {
	props: ['role'],
	template:
	'<div>' +
	    '<i> {{ role.id }} </i>' +
	    ' {{ role.role }} ' +
	'</div>'
});

Vue.component('roles-list', {
    props: ['roles'],
	template:
	'<div>' +
	    '<role-form :roles="roles" />' +
	    '<roles-row v-for="role in roles" :key="role.id" :role="role"/>' +
	'</div>',
	created: function() {
	    rolesApi.get().then(result =>
	        result.json().then(data =>
	            data.forEach(role => this.roles.push(role))
	        )
	    )
	}
});

var app = new Vue({
	el: '#app',
	template: '<roles-list :roles="roles" />',
	data: {
		roles: []
	}
});