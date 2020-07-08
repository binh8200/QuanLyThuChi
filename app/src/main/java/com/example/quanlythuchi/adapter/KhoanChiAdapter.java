package com.example.quanlythuchi.adapter;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.example.quanlythuchi.R;
import com.example.quanlythuchi.dao.KhoanChiDAO;
import com.example.quanlythuchi.dao.LoaiChiDAO;
import com.example.quanlythuchi.model.KhoanThuChi;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class KhoanChiAdapter extends BaseAdapter {
    Context context;
    ArrayList<KhoanThuChi> list;
    LoaiChiDAO loaiChiDAO;
    KhoanChiDAO khoanChiDAO;
    KhoanChiAdapter khoanChiAdapter;
    ListView lvkhoanchi;
    EditText edtTenKhoanChi;
    TextView edtNgayChi;
    EditText edtSoTien;
    EditText edtGhiChu;
    Spinner spLoaiChi;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    static final String TAG = "QLSV";

    public KhoanChiAdapter(Context context, ArrayList<KhoanThuChi> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            convertView = inflater.inflate(R.layout.khoanthuchi_layout, null);
            viewHolder.vhTvTenKhoanTC = convertView.findViewById(R.id.tvTenKhoanTC);
            viewHolder.vhNgayTC = convertView.findViewById(R.id.tvNgayTC);
            viewHolder.vhSoTien = convertView.findViewById(R.id.tvSoTien);
            viewHolder.vhGhiChu = convertView.findViewById(R.id.tvGhiChu);
            viewHolder.vhMaLoai = convertView.findViewById(R.id.tvMaLoai);
            viewHolder.vhIvEdit = convertView.findViewById(R.id.iv_edit_khoantc);
            viewHolder.vhIvDelete = convertView.findViewById(R.id.iv_delete_khoantc);

            lvkhoanchi = convertView.findViewById(R.id.lvLoaichi);

            viewHolder.vhIvEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final AlertDialog.Builder alertDialog = new AlertDialog.Builder(v.getContext());
                    //-----gán layout-----
                    LayoutInflater inflater = ((Activity) context).getLayoutInflater();
                    v = inflater.inflate(R.layout.edit_khoanchi_layout, null);
                    alertDialog.setView(v);

                    //-----anhxa-----
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
                                    context, android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                                    mDateSetListener,
                                    year, month, day
                            );
                            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                            dialog.show();
                        }
                    });

                    //-----Spinner-----
                    loaiChiDAO = new LoaiChiDAO(context);
                    List<String> list2 = loaiChiDAO.getAllMaTen();
                    ArrayAdapter<String> a = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, list2) {
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

                    //-----Lấy dữ liệu gán lên edittext-----
                    KhoanThuChi ktc = list.get(position);
                    edtTenKhoanChi.setText(ktc.getTenKhoanThuChi());
                    edtNgayChi.setText(ktc.getNgayThuChi());
                    edtSoTien.setText(ktc.getSoTien() + "");
                    edtGhiChu.setText(ktc.getGhiChu());

                    //-----Sửa-----
                    alertDialog.setPositiveButton("Sửa", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            boolean erro = true;
                            if ((edtTenKhoanChi.getText().toString().equals("") || edtTenKhoanChi.getText().toString() == null)
                                    && (edtNgayChi.getText().toString().equals("") || edtNgayChi.getText().toString() == null)
                                    && (edtSoTien.getText().toString().equals("") || edtSoTien.getText().toString() == null)
                                    && (edtGhiChu.getText().toString().equals("") || edtGhiChu.getText().toString() == null)) {
                                Toast.makeText(context, "Vui lòng nhập đủ thông tin !", Toast.LENGTH_SHORT).show();
                                erro = false;
                            } else if (edtTenKhoanChi.getText().toString().equals("") || edtTenKhoanChi.getText().toString() == null) {
                                Toast.makeText(context, "Vui lòng nhập tên khoản chi !", Toast.LENGTH_SHORT).show();
                                erro = false;
                            } else if (edtNgayChi.getText().toString().equals("") || edtNgayChi.getText().toString() == null) {
                                Toast.makeText(context, "Vui lòng chọn ngày chi !", Toast.LENGTH_SHORT).show();
                                erro = false;
                            } else if (edtSoTien.getText().toString().equals("") || edtSoTien.getText().toString() == null) {
                                Toast.makeText(context, "Vui lòng nhập số tiền !", Toast.LENGTH_SHORT).show();
                                erro = false;
                            } else if (edtSoTien.getText().toString() != "" || edtSoTien.getText().toString() != null) {
                                try {
                                    Integer.parseInt(edtSoTien.getText().toString());
                                } catch (NumberFormatException e) {
                                    Toast.makeText(context, "Số tiền phải là số !", Toast.LENGTH_SHORT).show();
                                    erro = false;
                                }
                            }

                            if (erro == true) {
                                KhoanThuChi ktc = list.get(position);
                                khoanChiDAO = new KhoanChiDAO(context);
                                ktc.setMaLoai(spLoaiChi.getSelectedItem().toString().substring(0, spLoaiChi.getSelectedItem().toString().indexOf(" - ")));
                                ktc.setTenKhoanThuChi(edtTenKhoanChi.getText().toString());
                                ktc.setNgayThuChi(edtNgayChi.getText().toString());
                                ktc.setSoTien(Integer.parseInt(edtSoTien.getText().toString()));
                                ktc.setGhiChu(edtGhiChu.getText().toString());
                                if (khoanChiDAO.suaKhoanChi(ktc) == -1) {
                                    Toast.makeText(context, "Sửa không thành công", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                Toast.makeText(context, "Sửa thành công", Toast.LENGTH_SHORT).show();
                                list.clear();
                                list.addAll(khoanChiDAO.getAllKhoanChi());
                                KhoanChiAdapter.this.notifyDataSetChanged();
                            }

                        }
                    });
                    alertDialog.setNegativeButton("Không sửa", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // dialog.cancel();
                        }
                    });
                    alertDialog.show();
                }
            });

            //-----Xóa-----
            viewHolder.vhIvDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Snackbar.make(((Activity) context).findViewById(R.id.khoanchi_layout), "Bạn có muốn xóa", 2000)
                            .setActionTextColor(Color.RED)
                            .setAction("yes", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    KhoanThuChi ktc = list.get(position);
                                    khoanChiDAO = new KhoanChiDAO(context);
                                    if (khoanChiDAO.xoaKhoanChi(ktc) == -1) {
                                        Toast.makeText(context, "Xóa không thành công", Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                    Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
                                    list.clear();
                                    list.addAll(khoanChiDAO.getAllKhoanChi());
                                    KhoanChiAdapter.this.notifyDataSetChanged();
                                }
                            })
                            .show();
                }
            });

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        KhoanThuChi ktc = list.get(position);
        viewHolder.vhTvTenKhoanTC.setText(ktc.getTenKhoanThuChi().toString());
        viewHolder.vhNgayTC.setText(ktc.getNgayThuChi().toString());
        viewHolder.vhSoTien.setText(ktc.getSoTien() + "");
        viewHolder.vhGhiChu.setText(ktc.getGhiChu().toString());
        viewHolder.vhMaLoai.setText(ktc.getMaLoai().toString());
        return convertView;
    }

    class ViewHolder {
        TextView vhTvTenKhoanTC;
        TextView vhNgayTC;
        TextView vhSoTien;
        TextView vhGhiChu;
        TextView vhMaLoai;
        ImageView vhIvEdit, vhIvDelete;
    }
}
