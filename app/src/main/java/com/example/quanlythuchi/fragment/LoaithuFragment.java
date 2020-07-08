package com.example.quanlythuchi.fragment;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;


import com.example.quanlythuchi.R;
import com.example.quanlythuchi.adapter.LoaiThuAdapter;
import com.example.quanlythuchi.dao.LoaiThuDAO;
import com.example.quanlythuchi.model.LoaiThuChi;

import java.util.ArrayList;

public class LoaithuFragment extends Fragment {
    ListView lvLoaithu;
    ArrayList<LoaiThuChi> list;
    LoaiThuAdapter loaiThuAdapter;
    LoaiThuDAO loaiThuDAO;


    public LoaithuFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_loaithu, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        lvLoaithu=view.findViewById(R.id.lvLoaithu);
        list=new ArrayList<>();
        loaiThuDAO=new LoaiThuDAO(getContext());
        list=loaiThuDAO.getAll();
        loaiThuAdapter=new LoaiThuAdapter(getContext(),list);
        lvLoaithu.setAdapter(loaiThuAdapter);

        FloatingActionButton floatingActionButton=getActivity().findViewById(R.id.FABThemloaithu);
        floatingActionButton.show();
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
                //-----gán layout-----
                LayoutInflater inflater = getActivity().getLayoutInflater();
                v = inflater.inflate(R.layout.add_loaithu_layout, null);
                alertDialog.setView(v);
                //-----ánh xạ-----
                final EditText edtMaloai=(EditText)v.findViewById(R.id.edtMaloai);
                final EditText edtTenloai = (EditText) v.findViewById(R.id.edtTenloai);

                //-----thêm-----
                alertDialog.setPositiveButton("Thêm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            boolean error =true;
                            if ((edtTenloai.getText().toString().equals("") || edtTenloai.getText().toString() == null) && (edtMaloai.getText().toString().equals("") || edtMaloai.getText().toString() == null)) {
                                Toast.makeText(getContext(), "Vui lòng nhập đủ thông tin !", Toast.LENGTH_SHORT).show();
                                error = false;
                            }else if (edtTenloai.getText().toString().equals("") || edtTenloai.getText().toString() == null) {
                                Toast.makeText(getContext(), "Vui lòng nhập tên loại !", Toast.LENGTH_SHORT).show();
                                error = false;
                            }else if (edtMaloai.getText().toString().equals("") || edtMaloai.getText().toString() == null) {
                                Toast.makeText(getContext(), "Vui lòng nhập mã loại !", Toast.LENGTH_SHORT).show();
                                error = false;
                            }

                            if(error==true){
                                LoaiThuChi ltc = new LoaiThuChi();
                                ltc.setMaLoai(edtMaloai.getText().toString());
                                ltc.setTenLoai(edtTenloai.getText().toString());
                                if (loaiThuDAO.themLoaiThu(ltc) == -1) {
                                    Toast.makeText(getContext(), "Mã loại bị trùng !", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                Toast.makeText(getContext(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                                list = loaiThuDAO.getAll();
                                loaiThuAdapter = new LoaiThuAdapter(getContext(), list);
                                lvLoaithu.setAdapter(loaiThuAdapter);
                            }

                        } catch (NullPointerException npe) {
                            Toast.makeText(getContext(), "Lỗi không xát định !", Toast.LENGTH_SHORT).show();
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
