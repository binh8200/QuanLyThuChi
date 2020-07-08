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
import com.example.quanlythuchi.adapter.KhoanChiAdapter;
import com.example.quanlythuchi.dao.KhoanChiDAO;
import com.example.quanlythuchi.dao.LoaiChiDAO;
import com.example.quanlythuchi.model.KhoanThuChi;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class KhoanchiFragment extends Fragment {

    ListView lvkhoanchi;
    ArrayList<KhoanThuChi> list;
    KhoanChiAdapter khoanChiAdapter;
    KhoanChiDAO khoanChiDAO;
    LoaiChiDAO loaiChiDAO;
    EditText edtTenKhoanChi, edtSoTien, edtGhiChu;
    Spinner spLoaiChi;
    TextView edtNgayChi;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    static final String TAG = "QLSV";


    public KhoanchiFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_khoanchi, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //-----ListView-----
        lvkhoanchi = view.findViewById(R.id.lvkhoanchi);
        list = new ArrayList<>();
        khoanChiDAO = new KhoanChiDAO(getContext());
        list = khoanChiDAO.getAllKhoanChi();
        khoanChiAdapter = new KhoanChiAdapter(getContext(), list);
        lvkhoanchi.setAdapter(khoanChiAdapter);

        //-----FloatingActionButton-----
        FloatingActionButton floatingActionButton = getActivity().findViewById(R.id.FABThemkhoanchi);
        floatingActionButton.show();
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
                //-----Gắn Layout-----
                LayoutInflater inflater = getActivity().getLayoutInflater();
                v = inflater.inflate(R.layout.add_khoanchi_layout, null);
                alertDialog.setView(v);
                //-----ánh xạ-----
                edtTenKhoanChi = v.findViewById(R.id.edtTenKhoanChi);
                edtNgayChi = v.findViewById(R.id.edtNgayChi);
                edtSoTien = v.findViewById(R.id.edtSoTien);
                edtGhiChu = v.findViewById(R.id.edtGhiChu);
                spLoaiChi = v.findViewById(R.id.spLoaiChi);

                //--DatePicker-----
                mDateSetListener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month = month + 1;
                        Log.d(TAG, "onDateSet:mm/dd/yyyy: " + dayOfMonth + "/" + month + "/" + year);
                        String date = "   " + dayOfMonth + "/" + month + "/" + year;
                        edtNgayChi.setText(date);
                    }
                };
                edtNgayChi.setOnClickListener(new View.OnClickListener() {
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

                //-----Spinner-----
                loaiChiDAO = new LoaiChiDAO(getContext());
                List<String> list2 = loaiChiDAO.getAllMaTen();
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
                spLoaiChi.setAdapter(a);

                //-----Button View in Dialog (add)-----
                alertDialog.setPositiveButton("Thêm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            boolean erro = true;
                            if ((edtTenKhoanChi.getText().toString().equals("") || edtTenKhoanChi.getText().toString() == null)
                                    && (edtNgayChi.getText().toString().equals("") || edtNgayChi.getText().toString() == null)
                                    && (edtSoTien.getText().toString().equals("") || edtSoTien.getText().toString() == null)
                                    && (edtGhiChu.getText().toString().equals("") || edtGhiChu.getText().toString() == null)) {
                                Toast.makeText(getContext(), "Vui lòng nhập đủ thông tin !", Toast.LENGTH_SHORT).show();
                                erro = false;
                            } else if (edtTenKhoanChi.getText().toString().equals("") || edtTenKhoanChi.getText().toString() == null) {
                                Toast.makeText(getContext(), "Vui lòng nhập tên khoản chi !", Toast.LENGTH_SHORT).show();
                                erro = false;
                            } else if (edtNgayChi.getText().toString().equals("") || edtNgayChi.getText().toString() == null) {
                                Toast.makeText(getContext(), "Vui lòng chọn ngày chi !", Toast.LENGTH_SHORT).show();
                                erro = false;
                            } else if (edtSoTien.getText().toString().equals("") || edtSoTien.getText().toString() == null) {
                                Toast.makeText(getContext(), "Vui lòng nhập số tiền !", Toast.LENGTH_SHORT).show();
                                erro = false;
                            } else if (edtSoTien.getText().toString() != "" || edtSoTien.getText().toString() != null) {
                                try {
                                    Integer.parseInt(edtSoTien.getText().toString());
                                } catch (NumberFormatException e) {
                                    Toast.makeText(getContext(), "Số tiền phải là số !", Toast.LENGTH_SHORT).show();
                                    erro = false;
                                }
                            }


                            if (erro == true) {
                                KhoanThuChi ktc = new KhoanThuChi();
                                ktc.setMaLoai(spLoaiChi.getSelectedItem().toString().substring(0,spLoaiChi.getSelectedItem().toString().indexOf(" - ")));
                                ktc.setTenKhoanThuChi(edtTenKhoanChi.getText().toString());
                                ktc.setNgayThuChi(edtNgayChi.getText().toString());
                                ktc.setSoTien(Integer.parseInt(edtSoTien.getText().toString()));
                                ktc.setGhiChu(edtGhiChu.getText().toString());
                                if (khoanChiDAO.themKhoanChi(ktc) == -1) {
                                    Toast.makeText(getContext(), "Thêm không thành công", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                Toast.makeText(getContext(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                                list = khoanChiDAO.getAllKhoanChi();
                                khoanChiAdapter = new KhoanChiAdapter(getContext(), list);
                                lvkhoanchi.setAdapter(khoanChiAdapter);
                            }

                        } catch (NullPointerException npe) {
                            Toast.makeText(getContext(), "Bạn chưa thêm loại chi!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                //-----cancel-----
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
