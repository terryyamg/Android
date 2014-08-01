package com.manlen.tutorials.pushnotifications;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class ListCommodity extends FragmentActivity {
	private Button close, bc1, bc2;
	private TextView cd1, cd2;
	private String[] s1 = new String[] { "���e��y", "���N�@��" };
	private int price;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list_commodity);

		close = (Button) findViewById(R.id.close);
		bc1 = (Button) findViewById(R.id.bc1);
		bc2 = (Button) findViewById(R.id.bc2);

		cd1 = (TextView) findViewById(R.id.cd1);
		cd2 = (TextView) findViewById(R.id.cd2);

		cd1.setText(s1[0] + "\n�զ⪺�A���o��W�A��������A�� \n" + "������`�B�n�ƥ��B�A�b�A���o�� \n"
				+ "�U�����U�A�i�{�X�X�n�������s \n" + "��ı�β��e�����ɨ��C");
		cd2.setText(s1[1] + "\n�@�ر����J�|���X���������A�J�f \n" + "��@�����@�ح��f�t�n�ܪ���X�� \n"
				+ "�G�A�������t�C");
		// �Ĥ@��ӫ~
		bc1.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				search(s1[0]);
			}
		});
		bc2.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				search(s1[1]);
			}
		});

		close.setOnClickListener(back); // ��^

	}

	// �j�M�ӫ~����
	void search(final String s) {

		ParseQuery<ParseObject> query = ParseQuery.getQuery("Commodity");
		query.whereEqualTo("commodity", s);
		query.getFirstInBackground(new GetCallback<ParseObject>() {
			public void done(ParseObject object, ParseException e) {
				if (e == null) {
					price = object.getInt("price");
					buy(s);
				} else {
				}
			}
		});

	}

	// �Ĥ@��
	void buy(String name) {
		Intent intent = new Intent(this, BuyConfirm.class);
		if (name.equals(s1[0])) {
			intent.putExtra("commodityName", s1[0]);
			intent.putExtra("price", price);
		} else if (name.equals(s1[1])) {
			intent.putExtra("commodityName", s1[1]);
			intent.putExtra("price", price);
		}
		startActivity(intent);
	}

	// ��^
	private OnClickListener back = new OnClickListener() {
		public void onClick(View v) {
			finish();
		}
	};

}
