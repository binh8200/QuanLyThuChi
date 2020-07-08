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
import com.example.quanlythuchi.adapter.LoaiChiAdapter;
import com.example.quanlythuchi.dao.LoaiChiDAO;
import com.example.quanlythuchi.model.LoaiThuChi;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoaichiFragment extends Fragment {
    ListView lvLoaichi;
    ArrayList<LoaiThuChi> list;
    LoaiChiAdapter loaiChiAdapter;
    LoaiChiDAO loaiChiDAO;


    public LoaichiFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_loaichi, container, false);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        lvLoaichi = view.findViewById(R.id.lvLoaichi);
        list = new ArrayList<>();
        loaiChiDAO = new LoaiChiDAO(getContext());
        list = loaiChiDAO.getAll();
        loaiChiAdapter = new LoaiChiAdapter(getContext(), list);
        lvLoaichi.setAdapter(loaiChiAdapter);

        FloatingActionButton floatingActionButton = getActivity().findViewById(R.id.FABThemloaichi);
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
                final EditText edtMaloai = (EditText) v.findViewById(R.id.edtMaloai);
                final EditText edtTenloai = (EditText) v.findViewById(R.id.edtTenloai);

                //-----thêm-----
                alertDialog.setPositiveButton("Thêm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            boolean error = true;
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

                            if (error == true) {
                                LoaiThuChi ltc = new LoaiThuChi();
                                ltc.setMaLoai(edtMaloai.getText().toString());
                                ltc.setTenLoai(edtTenloai.getText().toString());
                                if (loaiChiDAO.themLoaiChi(ltc) == -1) {
                                    Toast.makeText(getContext(), "Mã loại bị trùng !", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                Toast.makeText(getContext(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                                list = loaiChiDAO.getAll();
                                loaiChiAdapter = new LoaiChiAdapter(getContext(), list);
                                lvLoaichi.setAdapter(loaiChiAdapter);
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
