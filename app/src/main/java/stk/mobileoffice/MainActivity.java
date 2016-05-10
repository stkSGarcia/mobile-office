package stk.mobileoffice;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity
		implements NavigationView.OnNavigationItemSelectedListener {
	Toolbar toolbar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.drawer);

		toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

		DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
		drawer.setDrawerListener(toggle);
		toggle.syncState();

		NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
		navigationView.setNavigationItemSelectedListener(this);


//		getSupportActionBar().setTitle("商机");
	}

	@Override
	public void onBackPressed() {
		DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		if (drawer.isDrawerOpen(GravityCompat.START)) {
			drawer.closeDrawer(GravityCompat.START);
		} else {
			super.onBackPressed();
		}
	}

	@Override
	public boolean onNavigationItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.opportunity_menu:
				toolbar.setTitle("商机");
				OpportunityList opportunityList = new OpportunityList();
				getFragmentManager().beginTransaction().replace(R.id.content, opportunityList).commit();
				break;
			case R.id.customer_menu:
				toolbar.setTitle("客户");
				CustomerList customerList = new CustomerList();
				getFragmentManager().beginTransaction().replace(R.id.content, customerList).commit();
				break;
			case R.id.contract_menu:
				toolbar.setTitle("合同");
				ContractList contractList = new ContractList();
				getFragmentManager().beginTransaction().replace(R.id.content, contractList).commit();
				break;
			case R.id.business_menu:
				toolbar.setTitle("业务");
				Business business = new Business();
				getFragmentManager().beginTransaction().replace(R.id.content, business).commit();
				break;
			case R.id.product_menu:
				toolbar.setTitle("产品");
				ProductList productList = new ProductList();
				getFragmentManager().beginTransaction().replace(R.id.content, productList).commit();
				break;
			case R.id.contact_menu:
				toolbar.setTitle("联系人");
				ContractList contactList = new ContractList();
				getFragmentManager().beginTransaction().replace(R.id.content, contactList).commit();
				break;
			default:
				return super.onOptionsItemSelected(item);
		}
		DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		drawer.closeDrawer(GravityCompat.START);
		return true;
	}
}
