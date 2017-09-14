package com.example.lenovo.smsgo.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lenovo.smsgo.R;
import com.example.lenovo.smsgo.models.ModelSms;

import java.util.List;

import static com.example.lenovo.smsgo.R.drawable.correct;
import static com.example.lenovo.smsgo.R.drawable.wrong;

/**
 * Created by LENOVO on 8/09/2017.
 */

public class AdapterListUsers extends RecyclerView.Adapter<AdapterListUsers.UserViewHolder> {


    private List<ModelSms> lsModelSms;
    private Context context;
    private int posSeleccionada;

    public AdapterListUsers(Context context, List<ModelSms> lsModelSms)
    {
        this.context = context;
        this.lsModelSms = lsModelSms;
        this.posSeleccionada = -1;
    }

    public List<ModelSms> getLsModelSms() {
        return lsModelSms;
    }

    public void setLsModelSms(List<ModelSms> lsModelSms) {
        this.lsModelSms = lsModelSms;
    }

    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(context).inflate(R.layout.list_user, parent, false);
        return new UserViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(UserViewHolder holder, int position) {

        String mNumberUser = lsModelSms.get(position).getNumber();
        String mMessage = lsModelSms.get(position).getMessage();
        String mFullname = lsModelSms.get(position).getFullName();
        /*char[] getName = mMessage.toCharArray();
        for (char c:getName){
                char sam =c;
                if (getName.length>15){

                }
        }*/

        if (lsModelSms.get(position).getMessage_sent()){
            holder.imgCheck.setImageResource(R.drawable.correct);
        }else {
            holder.imgCheck.setImageResource(R.drawable.wrong);
        }

        holder.txtNumber.setText(mNumberUser);
        holder.txtName.setText(mFullname);
    }

    public void mReset(){
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return lsModelSms.size();
    }

    public class UserViewHolder extends RecyclerView.ViewHolder {

        protected TextView txtNumber;
        protected TextView txtName;
        protected ImageView imgCheck;
        public UserViewHolder(View itemView)
        {
            super(itemView);
            imgCheck = (ImageView) itemView.findViewById(R.id.ticket_icon);
            txtNumber = (TextView) itemView.findViewById(R.id.textView);
            txtName = (TextView) itemView.findViewById(R.id.textView2);
        }

    }
}
