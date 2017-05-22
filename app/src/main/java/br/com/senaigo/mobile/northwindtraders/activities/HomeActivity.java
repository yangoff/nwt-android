package br.com.senaigo.mobile.northwindtraders.activities;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.menu.ExpandedMenuView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import br.com.senaigo.mobile.northwindtraders.R;
import br.com.senaigo.mobile.northwindtraders.adapters.CategoryAdapter;
import br.com.senaigo.mobile.northwindtraders.entities.Category;
import br.com.senaigo.mobile.northwindtraders.persistence.CategoryDAO;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by bruno on 04/04/16.
 */
public class HomeActivity extends AppCompatActivity {


    //ButterKnife - injeção de dependência
    @BindView(R.id.txtCategoryId) public EditText txtCategoryId;
    @BindView(R.id.txtCategoryName) public EditText txtCategoryName;
    @BindView(R.id.txtCategoryDescription) public EditText txtCategoryDescription;
    @BindView(R.id.btnCategory) public Button btnCreateCategory;
    @BindView(R.id.btnDelete) public  Button btnDeleteCategory;

    protected Category category;
    protected List<Category> categories;
    protected ListView lv;
    protected CategoryDAO categoryDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        categoryDAO = new CategoryDAO(this);
        ButterKnife.bind(this);
        ButterKnife.setDebug(true);
        lv = (ListView) findViewById(R.id.listView);
        updateListView();
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Category c = categories.get(position);
                txtCategoryId.setText(c.getCategoryId().toString());
                txtCategoryName.setText(c.getCategoryName().toString());
                txtCategoryDescription.setText(c.getDescription());
                btnDeleteCategory.setVisibility(View.VISIBLE);
                btnCreateCategory.setText(getResources().getString(R.string.update));
            }
        });
        categoryDAO.close();

    }

    protected Category populateList(){
        Category category = new Category();
        category.setCategoryId(Integer.parseInt(txtCategoryId.getText().toString()));
        category.setCategoryName(txtCategoryName.getText().toString());
        category.setDescription(txtCategoryDescription.getText().toString());
        category.setPicture(BitmapFactory.decodeResource(getResources(), R.drawable.student_1));
        return category;
    }

    @OnClick(R.id.btnDelete) public void deleteCategory(View view){

        Category c = new Category();
        c.setCategoryId(Integer.parseInt(txtCategoryId.getText().toString()));
        c.setCategoryName(txtCategoryName.getText().toString());
        c.setDescription(txtCategoryDescription.getText().toString());
        categoryDAO.onRemove(c);
        btnDeleteCategory.setVisibility(View.INVISIBLE);
        btnCreateCategory.setText(getResources().getString(R.string.create));
        updateListView();
        categoryDAO.close();
        setEmptyText();

    }

    @OnClick(R.id.btnCategory)
    public void getCategory(View view) {

        if(btnCreateCategory.getText().toString().equals(getResources().getString(R.string.update))){
            categoryDAO.onUpdate(populateList());
            btnCreateCategory.setText(getResources().getString(R.string.create));
            btnDeleteCategory.setVisibility(View.INVISIBLE);

        }else{
            categoryDAO.onInsert(populateList());
        }
        setEmptyText();
        updateListView();
        categoryDAO.close();


    }
    public void updateListView(){
        categories = categoryDAO.onList(null);
        lv.setAdapter(new CategoryAdapter(getApplicationContext(), categories));
        categoryDAO.close();
    }
    public void setEmptyText(){
        txtCategoryId.setText("");
        txtCategoryName.setText("");
        txtCategoryDescription.setText("");
    }

}
