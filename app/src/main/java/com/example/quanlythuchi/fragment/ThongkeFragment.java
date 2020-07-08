package com.example.quanlythuchi.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.quanlythuchi.R;
import com.example.quanlythuchi.dao.KhoanChiDAO;
import com.example.quanlythuchi.dao.KhoanThuDAO;


/**
 * A simple {@link Fragment} subclass.
 */
public class ThongkeFragment extends Fragment {
    TextView edtTongSoTienThu,edtTongSoTienChi,edtTongTien;
    KhoanThuDAO khoanThuDAO;
    KhoanChiDAO khoanChiDAO;
    public ThongkeFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_thongke, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ///-----ánh xạ-----
        edtTongSoTienThu=view.findViewById(R.id.edtTongSoTienThu);
        edtTongSoTienChi=view.findViewById(R.id.edtTongSoTienChi);
        edtTongTien=view.findViewById(R.id.edtTongTien);

        khoanThuDAO=new KhoanThuDAO(getContext());
        khoanChiDAO=new KhoanChiDAO(getContext());

        //-----Tổng thu-----
        int tongtienthu=khoanThuDAO.tongSoTienThu();
        edtTongSoTienThu.setText(tongtienthu+"");

        //-----Tổng chi-----
        int tongtienchi=khoanChiDAO.tongSoTienChi();
        edtTongSoTienChi.setText(tongtienchi+"");

        //-----Còn lại-----
        int tongtien=tongtienthu-tongtienchi;
        edtTongTien.setText(tongtien+"");
    }

    public interface OnFragmentInteractionListener {
    }
}
