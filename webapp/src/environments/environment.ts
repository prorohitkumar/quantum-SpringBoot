// This file can be replaced during build by using the `fileReplacements` array.
// `ng build --prod` replaces `environment.ts` with `environment.prod.ts`.
// The list of file replacements can be found in `angular.json`.

export const environment = {
  production: false,
  BASE_URL: "http://localhost:8091/",
  content: [
    {
      menu: 'Home',
      action: ['Home'],
      route: '/dashboard',
      icon: 'home'
    },
    {
      menu: 'Create Project',
      action: ['CreateProject'],
      route: '/dashboard/createproject',
      icon: 'add'
    },
    {
      menu: 'User Profile',
      action: ['UserProfile'],
      route: '/dashboard/user',
      icon: 'fa fa-user'
    },
    {
      menu: 'My Projects',
      action: ['MyProjects'],
      // route: '/dashboard/project',
       icon: 'list',
      route: '/dashboard'
      // icon: 'fas fa-leaf'
    }
  ]
};

/*
 * For easier debugging in development mode, you can import the following file
 * to ignore zone related error stack frames such as `zone.run`, `zoneDelegate.invokeTask`.
 *
 * This import should be commented out in production mode because it will have a negative impact
 * on performance if an error is thrown.
 */
// import 'zone.js/dist/zone-error';  // Included with Angular CLI.
