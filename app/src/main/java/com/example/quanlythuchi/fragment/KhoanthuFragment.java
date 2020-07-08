package com.example.quanlythuchi.fragment;


import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.example.quanlythuchi.R;
import com.example.quanlythuchi.adapter.KhoanThuAdapter;
import com.example.quanlythuchi.dao.KhoanThuDAO;
import com.example.quanlythuchi.dao.LoaiThuDAO;
import com.example.quanlythuchi.model.KhoanThuChi;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class KhoanthuFragment extends Fragment {

    ListView lvkhoanthu;
    ArrayList<KhoanThuChi> list;
    KhoanThuAdapter khoanThuAdapter;
    KhoanThuDAO khoanThuDAO;
    LoaiThuDAO loaiThuDAO;
    EditText edtTenKhoanThu, edtSoTien, edtGhiChu;
    TextView edtNgaythu;
    Spinner spLoaiThu;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    static final String TAG = "QLSV";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    //-----OptionItem-----
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.search:

                break;
            case R.id.setting:
                lvkhoanthu = getActivity().findViewById(R.id.lvkhoanthu);
                list = new ArrayList<>();
                khoanThuDAO = new KhoanThuDAO(getContext());
                list = khoanThuDAO.sapXepTheoSoTien();
                khoanThuAdapter = new KhoanThuAdapter(getContext(), list);
                lvkhoanthu.setAdapter(khoanThuAdapter);
                break;
            case R.id.logout:
                Toast.makeText(getContext(), "Logout", Toast.LENGTH_SHORT).show();
                break;
        }

        return false;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_khoanthu, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //-----ListView-----
        lvkhoanthu = view.findViewById(R.id.lvkhoanthu);
        list = new ArrayList<>();
        khoanThuDAO = new KhoanThuDAO(getContext());
        list = khoanThuDAO.getAllKhoanThu();
        khoanThuAdapter = new KhoanThuAdapter(getContext(), list);
        lvkhoanthu.setAdapter(khoanThuAdapter);

        //-----FloatingActionButton-----
        FloatingActionButton floatingActionButton = getActivity().findViewById(R.id.FABThemkhoanthu);
        floatingActionButton.show();
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
                //gắn layout vào view
                final LayoutInflater inflater = getActivity().getLayoutInflater();
                v = inflater.inflate(R.layout.add_khoanthu_layout, null);
                alertDialog.setView(v);
                //ánh xạ
                edtTenKhoanThu = v.findViewById(R.id.edtTenKhoanThu);
                edtNgaythu = v.findViewById(R.id.edtNgayThu);
                edtSoTien = v.findViewById(R.id.edtSoTien);
                edtGhiChu = v.findViewById(R.id.edtGhiChu);
                spLoaiThu = v.findViewById(R.id.spLoaiThu);

                //-----DatePicker-----
                mDateSetListener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month = month + 1;
                        Log.d(TAG, "onDateSet:mm/dd/yyyy: " + dayOfMonth + "/" + month + "/" + year);
                        String date = "   " + dayOfMonth + "/" + month + "/" + year;
                        edtNgaythu.setText(date);
                    }
                };
                edtNgaythu.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Calendar calendar = Calendar.getInstance();
                        int year = calendar.get(Calendar.YEAR);
                        int month = calendar.get(Calendar.MONTH);
                        int day = calendar.get(Calendar.DAY_OF_MONTH);
                        DatePickerDialog dialog = new DatePickerDialog(
                                getContext(), android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                                mDateSetListener,
                                year, month, day
                        );
                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        dialog.show();
                    }
                });

                //Tạo spinner chọn mã loại thu
                loaiThuDAO = new LoaiThuDAO(getContext());
                List<String> list2 = loaiThuDAO.getAllMaTen();
                ArrayAdapter<String> a = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, list2) {
                    @Override
                    public View getView(int position, View convertView, ViewGroup parent) {
                        View view = super.getView(position, convertView, parent);
                        TextView textView = view.findViewById(android.R.id.text1);
                        if (position % 2 == 0)
                            textView.setTextColor(Color.BLACK);
                        else
                            textView.setTextColor(Color.BLACK);
                        return view;
                    }
                };
                spLoaiThu.setAdapter(a);

                //gắn thêm nút và hiển thị dialog
                alertDialog.setPositiveButton("Thêm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            boolean error = true;
                            if((edtTenKhoanThu.getText().toString().equals("") || edtTenKhoanThu.getText().toString() == null)
                            && (edtNgaythu.getText().toString().equals("") || edtNgaythu.getText().toString() == null)
                            && (edtSoTien.getText().toString().equals("") || edtSoTien.getText().toString() == null)
                            && (edtGhiChu.getText().toString().equals("") || edtGhiChu.getText().toString() == null)) {
                                Toast.makeText(getContext(), "Vui lòng nhập đủ thông tin !", Toast.LENGTH_SHORT).show();
                                error = false;
                            } else if (edtTenKhoanThu.getText().toString().equals("") || edtTenKhoanThu.getText().toString() == null){
                                Toast.makeText(getContext(), "Vui lòng nhập tên khoản thu !", Toast.LENGTH_SHORT).show();
                                error = false;
                            }else if (edtNgaythu.getText().toString().equals("") || edtNgaythu.getText().toString() == null){
                                Toast.makeText(getContext(), "Vui lòng chọn ngày thu !", Toast.LENGTH_SHORT).show();
                                error = false;
                            }else if (edtSoTien.getText().toString().equals("") || edtSoTien.getText().toString() == null){
                                Toast.makeText(getContext(), "Vui lòng nhập số tiền !", Toast.LENGTH_SHORT).show();
                                error = false;
                            }else if (edtSoTien.getText().toString() != "" || edtSoTien.getText().toString() != null) {
                                try {
                                    Integer.parseInt(edtSoTien.getText().toString());
                                } catch (NumberFormatException e) {
                                    Toast.makeText(getContext(), "Số tiền phải là số !", Toast.LENGTH_SHORT).show();
                                    error = false;
                                }
                            }

                            if (error == true) {
                                KhoanThuChi ktc = new KhoanThuChi();
                                ktc.setMaLoai(spLoaiThu.getSelectedItem().toString().substring(0,spLoaiThu.getSelectedItem().toString().indexOf(" - ")));
                                ktc.setTenKhoanThuChi(edtTenKhoanThu.getText().toString());
                                ktc.setNgayThuChi(edtNgaythu.getText().toString());
                                ktc.setSoTien(Integer.parseInt(edtSoTien.getText().toString()));
                                ktc.setGhiChu(edtGhiChu.getText().toString());
                                if (khoanThuDAO.themKhoanThu(ktc) == -1) {
                                    Toast.makeText(getContext(), "Thêm không thành công", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                Toast.makeText(getContext(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                                list = khoanThuDAO.getAllKhoanThu();
                                khoanThuAdapter = new KhoanThuAdapter(getContext(), list);
                                lvkhoanthu.setAdapter(khoanThuAdapter);
                            }

                        } catch (NullPointerException npe) {
                            Toast.makeText(getContext(), "Bạn chưa thêm loại Thu !", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                alertDialog.setNegativeButton("Thoát", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                alertDialog.show();
            }
        });


    }

}
