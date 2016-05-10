package stk.mobileoffice;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import stk.mobileoffice.business.BusinessFragment;
import stk.mobileoffice.contact.ContactFragment;
import stk.mobileoffice.contract.ContractFragment;
import stk.mobileoffice.customer.CustomerFragment;
import stk.mobileoffice.opportunity.OpportunityFragment;
import stk.mobileoffice.product.ProductFragment;

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

		getSupportActionBar().setTitle("商机");
		getFragmentManager().beginTransaction().replace(R.id.content, new OpportunityFragment()).commit();
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
				OpportunityFragment opportunityFragment = new OpportunityFragment();
				getFragmentManager().beginTransaction().replace(R.id.content, opportunityFragment).commit();
				break;
			case R.id.customer_menu:
				toolbar.setTitle("客户");
				CustomerFragment customerFragment = new CustomerFragment();
				getFragmentManager().beginTransaction().replace(R.id.content, customerFragment).commit();
				break;
			case R.id.contract_menu:
				toolbar.setTitle("合同");
				ContractFragment contractFragment = new ContractFragment();
				getFragmentManager().beginTransaction().replace(R.id.content, contractFragment).commit();
				break;
			case R.id.business_menu:
				toolbar.setTitle("业务");
				BusinessFragment businessFragment = new BusinessFragment();
				getFragmentManager().beginTransaction().replace(R.id.content, businessFragment).commit();
				break;
			case R.id.product_menu:
				toolbar.setTitle("产品");
				ProductFragment productFragment = new ProductFragment();
				getFragmentManager().beginTransaction().replace(R.id.content, productFragment).commit();
				break;
			case R.id.contact_menu:
				toolbar.setTitle("联系人");
				ContactFragment contactFragment = new ContactFragment();
				getFragmentManager().beginTransaction().replace(R.id.content, contactFragment).commit();
				break;
			default:
				return super.onOptionsItemSelected(item);
		}
		DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		drawer.closeDrawer(GravityCompat.START);
		return true;
	}
}
