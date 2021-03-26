package lebah.module;

import java.util.List;

import lebah.db.entity.Menu;
import lebah.db.entity.Role;
import lebah.portal.action.Command;

/**
 * 
 * @author Shamsul Bahrin
 *
 */
public class SetupMenusModule extends LebahAdminModule {
	
	String path = "vtl/modules/setupMenus";
	
	
	@Override
	public void preProcess() {
		super.preProcess();
		List<Role> roles = db.list("select r from Role r");
		context.put("roles", roles);
		
		System.out.println("menu command = " + command);
	}

	@Override
	public String start() {
		try {
			list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return path + "/start.vm";
	}
	
	@Command("list")
	public String list() throws Exception {
		List<Menu> menus = db.list("select m from Menu m where m.parent is null order by m.orderNo");
		context.put("menus", menus);
		context.remove("parent");
		return path + "/list.vm";
	}
	
	@Command("listgroup")
	public String listgroup() throws Exception {
		System.out.println("listgroup");
		String parentId = getParam("parentId");
		Menu menu = db.find(Menu.class, parentId);
		
		System.out.println("parent = " + menu);
		
		context.put("parent", menu);
		
		List<Menu> menus = db.list("select m from Menu m where m.parent.id = '" + menu.getId() + "' order by m.orderNo");
		context.put("menus", menus);
		
		//context.put("menus", menu.getMenus());
		return path + "/list.vm";
	}
	
	@Command("add")
	public String add() throws Exception {
		String parentId = getParam("parentId");
		Menu parent = db.find(Menu.class, parentId);
		context.put("parent", parent);
		
		String menuId = getParam("menuId");
		boolean add = "".equals(menuId);
		
		context.remove("menu");
		if ( !add ) {
			Menu menu = db.find(Menu.class, menuId);
			context.put("menu", menu);
		}
		
		return path + "/add.vm";
	}
	
	@Command("addgroup")
	public String addgroup() throws Exception {
		String menuId = getParam("menuId");
		boolean add = "".equals(menuId);
		
		if ( !add ) {
			Menu menu = db.find(Menu.class, menuId);
			context.put("menu", menu);
		} else {
			context.remove("menu");
		}
		
		return path + "/addgroup.vm";
	}
	
	@Command("save")
	public String save() throws Exception {
		
		String parentId = getParam("parentId");
		Menu parent = db.find(Menu.class, parentId);
		
		System.out.println("group parent = " + parent);
		
		String menuId = getParam("menuId");
		boolean add = "".equals(menuId);
		
		String className = getParam("menu_className");
		String title = getParam("menu_title");
		String icon = getParam("menu_icon");
		String menus_size = getParam("menus_size");
		
		int menuSize = Integer.parseInt(menus_size);
		menuSize++;
		
		Menu menu = add ? new Menu() : db.find(Menu.class, menuId);
		
		menu.setModuleClassName(className);
		menu.setTitle(title);
		menu.setIcon(icon);
		
		if ( add ) {
			menu.setOrderNo(menuSize);
			if ( parent != null ) menu.setParent(parent);
		}
		
		if ( !add ) {
			menu.getRoles().clear();
		}
	
		String[] roles = request.getParameterValues("role_ids");
		if ( roles != null ) {
			for ( String id : roles ) {
				Role role = db.find(Role.class, id);
				menu.getRoles().add(role);
			}
		}
		
		if ( parent != null ) {
			if ( add ) {
				parent.getMenus().add(menu);
			}
		}
		
		db.ifAdd(add).saveOrUpdate(menu);
		
		
		if ( parent != null ) return listgroup();
		return list();
	}
	
	@Command("savegroup")
	public String savegroup() throws Exception {
		
		String menuId = getParam("menuId");
		boolean add = "".equals(menuId);
		
		String title = getParam("menu_title");
		String icon = getParam("menu_icon");
		String menus_size = getParam("menus_size");
		
		int menuSize = Integer.parseInt(menus_size);
		menuSize++;
		
		Menu menu = add ? new Menu() : db.find(Menu.class, menuId);
		
		menu.setTitle(title);
		menu.setIcon(icon);
		menu.setGrouped(1);
		
		if ( add ) menu.setOrderNo(menuSize);
		db.ifAdd(add).saveOrUpdate(menu);
		
		List<Menu> menus = db.list("select m from Menu m where m.parent.id = '" + menu.getId() + "' order by m.orderNo");
		context.put("menus", menus);
		context.put("parent", menu);
		
		return path + "/list.vm";
		
	}
	
	@Command("delete")
	public String delete() throws Exception {
		
		String menuId = getParam("menuId");
		Menu menu = db.find(Menu.class, menuId);
		
		String parentId = getParam("parentId");
		if ( !"".equals(parentId) ) {
			Menu parent = db.find(Menu.class, parentId);
			parent.getMenus().remove(menu);
			
			try {
				db.delete(menu);
				db.update(parent);
				
			} catch ( Exception e ) {
				e.printStackTrace();
			}
			
			System.out.println("deleted child menu");
			return listgroup();
		}
		else {
			db.delete(menu);
			return list();
		}

	}
	
	@Command("updateOrder")
	public String updateOrder() throws Exception {
		String[] menuIds = request.getParameterValues("menu_ids");
		if ( menuIds != null ) {
			int i = 0;
			for ( String id : menuIds ) {
				Menu menu = db.find(Menu.class, id);
				menu.setOrderNo(i);
				db.update(menu);
				i++;
			}
		}
		String parentId = getParam("parentId");
		if ( !"".equals(parentId) ) return listgroup();
		return list();
	}

}
