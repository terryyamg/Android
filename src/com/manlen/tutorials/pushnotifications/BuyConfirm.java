package com.manlen.tutorials.pushnotifications;

import java.util.Calendar;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class BuyConfirm extends FragmentActivity {
	private Button close, confirm;
	private ImageButton plus, minus;
	private EditText number;
	private TextView name, money,tv1,tv2,tv3,tv4,tv5;
	private DatePicker DatePicker;
	private String Year, Mon, Day, arrivalTime, commodityName, store;
	private RadioGroup timeChose;
	private RadioButton morning,afternoon,night;
	private int price, numberIndex, totalPrice, nowDate, choseDate;
	Typeface fontch;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.buy_confirm);
		/* �r�� */
		fontch = Typeface.createFromAsset(getAssets(), "fonts/wt034.ttf");
		
		close = (Button) findViewById(R.id.close);
		confirm = (Button) findViewById(R.id.confirm);
		plus = (ImageButton) findViewById(R.id.plus);
		minus = (ImageButton) findViewById(R.id.minus);
		number = (EditText) findViewById(R.id.number);
		name = (TextView) findViewById(R.id.name);
		money = (TextView) findViewById(R.id.money);
		tv1 = (TextView) findViewById(R.id.tv1);
		tv2 = (TextView) findViewById(R.id.tv2);
		tv3 = (TextView) findViewById(R.id.tv3);
		tv4 = (TextView) findViewById(R.id.tv4);
		tv5 = (TextView) findViewById(R.id.tv5);
		timeChose = (RadioGroup) findViewById(R.id.timeChose);
		morning = (RadioButton) findViewById(R.id.morning);
		afternoon = (RadioButton) findViewById(R.id.afternoon);
		night = (RadioButton) findViewById(R.id.night);
		
		close.setTypeface(fontch);
		confirm.setTypeface(fontch);
		name.setTypeface(fontch);
		money.setTypeface(fontch);
		tv1.setTypeface(fontch);
		tv2.setTypeface(fontch);
		tv3.setTypeface(fontch);
		tv4.setTypeface(fontch);
		tv5.setTypeface(fontch);
		morning.setTypeface(fontch);
		afternoon.setTypeface(fontch);
		night.setTypeface(fontch);
		/* ���o�ӫ~�W�ٻP���� */
		Intent intent = getIntent();
		store = intent.getStringExtra("store"); // ���o�ө��W��
		commodityName = intent.getStringExtra("commodityName"); // ���o�ӫ~�W��
		price = intent.getIntExtra("price", 1); // ���o��@����
		
		name.setText(commodityName); // �ӫ~�W��

		number.addTextChangedListener(new TextWatcher() {
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub
				/* �p���`�� */
				try {
					if (numberIndex < 0) {
						numberIndex = Integer.parseInt(number.getText()
								.toString());
						numberIndex = 0;
						money.setText(String.valueOf(numberIndex));
					} else if (numberIndex >= 0) {
						numberIndex = Integer.parseInt(number.getText()
								.toString());
						totalPrice = numberIndex * price; // �`��=�ƶq*��@����
						money.setText(String.valueOf(totalPrice));
					}
				} catch (Exception e) {

				}

			}
		});

		/* �ƶq */

		plus.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				numberIndex++;
				number.setText(String.valueOf(numberIndex));
				/* �p���`�� */
				totalPrice = numberIndex * price; // �`��=�ƶq*��@����
				money.setText(String.valueOf(totalPrice));
			}
		});

		minus.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				numberIndex--;
				if (numberIndex < 0) { // �ƶq�Ȥ��ର�t
					numberIndex = 0;
					number.setText(String.valueOf(numberIndex));
				}
				number.setText(String.valueOf(numberIndex));
				/* �p���`�� */
				totalPrice = numberIndex * price; // �`��=�ƶq*��@����
				money.setText(String.valueOf(totalPrice));
			}
		});

		/* ��f��� */
		Calendar TodayDate = Calendar.getInstance(); // �z�LCalendar�������
		int sYear = TodayDate.get(Calendar.YEAR); // �@�}�ҳn��Y���o�~���ƭ�
		int sMon = TodayDate.get(Calendar.MONTH) + 1; // �@�}�ҳn��Y���o�몺�ƭ�
														// �몺�_�l�O0�A�ҥH+1.
		int sDay = TodayDate.get(Calendar.DAY_OF_MONTH);// �@�}�ҳn��Y���o�骺�ƭ�
		// �N���o���Ʀr�নString.
		Year = DateFix(sYear);
		Mon = DateFix(sMon);
		Day = DateFix(sDay);
		nowDate = Integer.parseInt(Year + Mon + Day);// �{�b���
		choseDate = nowDate;
		DatePicker = (DatePicker) findViewById(R.id.DatePicker);
		DatePicker.init(TodayDate.get(Calendar.YEAR),
				TodayDate.get(Calendar.MONTH),
				TodayDate.get(Calendar.DAY_OF_MONTH),
				// DatePicker�~������A�|Ĳ�o�@�H�U���Ʊ��C
				new DatePicker.OnDateChangedListener() {
					@Override
					public void onDateChanged(DatePicker view, int year,
							int monthOfYear, int dayOfMonth) {
						Year = DateFix(year);
						Mon = DateFix(monthOfYear + 1); // �몺��l�O0�A�ҥH���[ 1�C
						Day = DateFix(dayOfMonth);
						choseDate = Integer.parseInt(Year + Mon + Day);
					}
				});

		// ��ܮɬq
		timeChose.setOnCheckedChangeListener(listener);

		// �T�{
		confirm.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				sentToLogin();
			}
		});
		// ��^
		close.setOnClickListener(back);
	}

	// ��kDateFix:�N�ǰe�i�Ӫ��~����নString�A�b�P�_�O�_�e���ݭn�[0�C
	private static String DateFix(int c) {
		if (c >= 10)
			return String.valueOf(c);
		else
			return "0" + String.valueOf(c);
	}

	// ��ܮɬq
	private RadioGroup.OnCheckedChangeListener listener = new RadioGroup.OnCheckedChangeListener() {

		@Override
		public void onCheckedChanged(RadioGroup group, int checkedId) {
			// TODO Auto-generated method stub
			switch (checkedId) {
			case R.id.morning:
				arrivalTime = "���W";
				break;
			case R.id.afternoon:
				arrivalTime = "�U��";
				break;
			case R.id.night:
				arrivalTime = "�ߤW";
				break;

			}

		}

	};

	// �e�X
	void sentToLogin() {

		if (numberIndex == 0) { // �ƶq�P�_
			Toast.makeText(getBaseContext(), "�ƶq���i��0", Toast.LENGTH_SHORT)
					.show();
		} else if (nowDate > choseDate) { // �{�b�ɶ� �� ��ܮɶ� ��
			Toast.makeText(getBaseContext(), "������i�����ѥH�e", Toast.LENGTH_SHORT)
					.show();
		} else if (arrivalTime == null) { // �ɬq�P�_
			Toast.makeText(getBaseContext(), "�ɬq�|�����", Toast.LENGTH_SHORT)
					.show();
		} else {
			Intent intent = new Intent(this, LoginGeneral.class);
			intent.putExtra("store", store); // �ө��W��
			intent.putExtra("commodityName", commodityName); // �ӫ~�W��
			intent.putExtra("numberIndex", numberIndex); // �ƶq
			intent.putExtra("totalPrice", totalPrice); // �`��
			intent.putExtra("arrivalDate", Year + Mon + Day); // ���f���
			intent.putExtra("arrivalTime", arrivalTime); // ���f���
			startActivity(intent);
		}

	}

	// ��^
	private OnClickListener back = new OnClickListener() {
		public void onClick(View v) {
			finish();
		}
	};
}
