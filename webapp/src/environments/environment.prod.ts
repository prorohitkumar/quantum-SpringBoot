export const environment = {
  production: true,
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
      menu: 'Create Team',
      action: ['CreateTeam'],
      route: '/dashboard',
      icon: 'fa fa-user-plus'
    },
    {
      menu: 'My Projects',
      action: ['MyProjects'],
      route: '/dashboard',
      icon: 'list'
    }
  ]
};
