package com.manlen.tutorials.pushnotifications;

import kankan.wheel.widget.OnWheelChangedListener;
import kankan.wheel.widget.OnWheelScrollListener;
import kankan.wheel.widget.WheelView;
import kankan.wheel.widget.adapters.AbstractWheelTextAdapter;
import kankan.wheel.widget.adapters.ArrayWheelAdapter;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class WheelWidget extends Activity {
	// Scrolling flag
	private boolean scrolling = false;
	private int indexFor1;
	private Button goButton;
	private int no1 = 1, no2 = 0, no3 = 0;
	private TextView wheeltv;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		/* �r�� */
		Typeface fontch = Typeface.createFromAsset(getAssets(), "fonts/wt034.ttf");
		
		setContentView(R.layout.cities_layout);
		wheeltv = (TextView) findViewById(R.id.wheeltv);
		goButton = (Button) findViewById(R.id.goButton);
		
		wheeltv.setTypeface(fontch); //�r��
		goButton.setTypeface(fontch);
		
		goButton.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {

				// TODO Auto-generated method stub
				goToGoogleMap();
			}
		});
		final WheelView wheel1 = (WheelView) findViewById(R.id.wheel1);// �Ĥ@��
		wheel1.setVisibleItems(3);
		wheel1.setViewAdapter(new CountryAdapter(this));

		final String cities[][] = new String[][] { // �ĤG�Ӧr
		// 0��
				new String[] { "��", "��", "��", "��", "�|", "��" },
				// 1�x�_
				new String[] { "��", "��", "��", "��", "�|", "��" },
				// 2�s�_
				new String[] { "��", "��", "��", "��", "�|", "��" },
				// 3���
				new String[] { "��", "��", "��", "��", "�|", "��" },
				// 4�s�˥�
				new String[] { "��", "��", "��", "��", "�|", "��" },
				// 5�s�˿�
				new String[] { "��", "��", "��", "��", "�|", "��" },
				// 6�]��
				new String[] { "��", "��", "��", "��", "�|", "��" },
				// 7�x��
				new String[] { "��", "��", "��", "��", "�|", "��" },
				// 8�n��
				new String[] { "��", "��", "��", "��", "�|", "��" },
				// 9����
				new String[] { "��", "��", "��", "��", "�|", "��" },
				// 10���L
				new String[] { "��", "��", "��", "��", "�|", "��" },
				// 11�Ÿq
				new String[] { "��", "��", "��", "��", "�|", "��" },
				// 12�x�n
				new String[] { "��", "��", "��", "��", "�|", "��" },
				// 13����
				new String[] { "��", "��", "��", "��", "�|", "��" },
				// 14�̪F
				new String[] { "��", "��", "��", "��", "�|", "��" },
				// 15�x�F
				new String[] { "��", "��", "��", "��", "�|", "��" },
				// 16�Ὤ
				new String[] { "��", "��", "��", "��", "�|", "��" },
				// 17�y��
				new String[] { "��", "��", "��", "��", "�|", "��" },
				// 18���
				new String[] { "��", "��", "��", "��", "�|", "��" },
				// 19����
				new String[] { "��", "��", "��", "��", "�|", "��" },
				// 20�s��
				new String[] { "��", "��", "��", "��", "�|", "��" } };
		final String storeName[][][] = new String[][][] { // �ĤG�Ӧr
		// 0��
				new String[][] {
						new String[] { "99��a�򶩧��@��", "99��a�򶩪F�H��", "99��a�C�����w��",
								"99��a�򶩸q�G��", "99��a�򶩼֤@��", "99��a�򶩪�����",
								"99��a�C���ʺ֩�", "99��a�򶩷s�ש�" },
						new String[] { "�򶩦�" }, new String[] { "�򶩦�" },
						new String[] { "�򶩦�" }, new String[] { "�򶩨|" },
						new String[] { "�򶩼�" } },
				// 1�x�_
				new String[][] {
						new String[] { "99��a�x�_���_�_��", "99��a��]��s��", "99��a�x�_�w�M��",
								"99��a�F��d�֩�", "99��a�x�_�ʵة�", "99��a�x�_������",
								"99��a�x�_���q��", "99��a�����w��", "99��a�x�_�æN��",
								"99��a��]�q����", "99��a�U�ظU�j��", "99��a�x�_�s����",
								"99��a�x�_���㩱", "99��a�x�_�a�P��", "99��a�x�_���ک�",
								"99��a�x�_���ͩ�", "99��a�x�_�N�L��", "99��a�x�_�A�{��",
								"99��a�x�_���w��", "99��a�x�_���ة�", "99��a�x�_���w�F��",
								"99��a����d�穱", "99��a�x�_�Ŭw��", "99��a�x�_�~����",
								"99��a�U�ؼs�{��", "99��a�h�L�C�橱", "99��a�x�_�B�n��",
								"99��a�_��|����", "99��a�x�_������", "99��a�ѥ��w�橱",
								"99��a�x�_�P�w��", "99��a�x�_�M���F��", "99��a�x�_�_����",
								"99��a�x�_�ìK��", "99��a�x�_�T����", "99��a�_�뤤�M��",
								"99��a�h�L��L��", "99��a�j�w�L����", "99��a�x�_��i�F��",
								"99��a�x�_�L���n�ʩ�", "99��a�h�L��e��" },
						new String[] { "�x�_��" }, new String[] { "�x�_��" },
						new String[] { "�x�_��" }, new String[] { "�x�_�|" },
						new String[] { "�x�_��" } },
				// 2�s�_
				new String[][] {
						new String[] { "99��a�éM�O����", "99��a�éM�ís��", "99��a�éM���˩�",
								"99��a�éM�o�M��", "99��a���M��q��", "99��a���M�n�ة�",
								"99��a���M���y��", "99��a���M���s��", "99��a���M���n��",
								"99��a�s���Ѽ橱", "99��a�O�����w��", "99��a�éM������",
								"99��a�T��������", "99��a�T�����ة�", "99��a�T�����F��",
								"99��a�O�������", "99��a�T�����s��", "99��a�T��������",
								"99��a�T��������", "99��a�s�����v��", "99��a��L���s��",
								"99��a�s��������", "99��a�s��������", "99��aĪ�w������",
								"99��a�s���w�d��", "99��a�s���C�i��", "99��a�s���s�w��",
								"99��a�s���s����", "99��a�s���I�ꩱ", "99��a�s���Ʀ���",
								"99��a�L�f�˪L��", "99��a�s�����֩�", "99��a�s��������",
								"99��a�O���|����", "99��a�O���j����", "99��a�O���|�t��",
								"99��a�O����Ʃ�", "99��a�a�q�a�穱", "99��a�O���j�P��",
								"99��a�O�����s��", "99��a�O���n����", "99��a�O���s����",
								"99��a�O�����Ʃ�", "99��aĪ�w���ة�", "99��aĪ�w�_����",
								"99��aĪ�w���w��", "99��a�g���Υ���", "99��a�U�����h��",
								"99��a��L���ة�", "99��a��L�O�w��", "99��a��L������",
								"99��a�T�����R��", "99��a�������", "99��a����s�x����",
								"99��a����̾�", "99��a����ئ���", "99��a�x�_�K����",
								"99��a�x�_�`�|��", "99��a���s���s��", "99��a�s���ئw��",
								"99��a���s���ө�", "99��a�x�_���s��", "99��a���Ѧ�����",
								"99��a�H���˳�", "99��a�H�����s��", "99��a�H���ǩ���",
								"99��a�T�l���ͩ�", "99��a�T�l��Ʃ�", "99��a�T�l���ة�",
								"99��a�a�q��y��", "99��a��ک��O��", "99��a�O���T����",
								"99��a�s���w�M��" }, new String[] { "�s�_��" },
						new String[] { "�s�_��" }, new String[] { "�s�_��" },
						new String[] { "�s�_�|" }, new String[] { "�s�_��" }, },
				// 3���
				new String[][] {
						new String[] { "99��a���_����", "99��a��餤�ة�", "99��a��鿤����",
								"99��a���g�ꩱ", "99��a�����a��", "99��a���j�~��",
								"99��a����s�w��", "99��a��餤����", "99��a���c���쩱",
								"99��a���c���ة�", "99��a���c�����F��", "99��a���c������",
								"99��a���c�s�O��", "99��a���c����(���a)��", "99��a���c���Ʃ�",
								"99��a���c�s����", "99��a���c���s��", "99��a���c������",
								"99��a�K�w���ة�", "99��a�����ש�", "99��a�s�Τ��s��",
								"99��a���c�c����", "99��a���U�ة�", "99��a���K�w��",
								"99��a���֦˩�", "99��a�n�r�n�˩�", "99��a���n�r��",
								"99��a�j�餤�s��", "99��a�[���s�Y��", "99��a���c���橱",
								"99��a�����s����", "99��a�������s��", "99��a��������",
								"99��a�L�f������", "99��a�s��_�s��", "99��a�j�s�s�ة�",
								"99��a��������", "99��a���������", "99��a�����H�ߩ�",
								"99��a�����Ʃ�", "99��a���c�֦{��" }, { "����" },
						new String[] { "����" }, new String[] { "����" },
						new String[] { "���|" }, new String[] { "����" } },
				// 4�s�˥�
				new String[][] {
						new String[] { "99��a�s�˦ˬ쩱", "99��a�s�˥��v��", "99��a�s�ˤ�����",
								"99��a�s�˸g�ꩱ", "99��a�s�ˤ��ة�" },
						new String[] { "�s�˥���" }, new String[] { "�s�˥���" },
						new String[] { "�s�˥���" }, new String[] { "�s�˥��|" },
						new String[] { "�s�˥���" } },
				// 5�s�˿�
				new String[][] {
						new String[] { "99��a�˥_�շR��", "99��a�˥_������", "99��a�s�׫ؿ���",
								"99��a��f������", "99��a�˪F���K��", "99��a���西�q��",
								"99��a��f���ة�" }, new String[] { "�s�˿���" },
						new String[] { "�s�˿���" }, new String[] { "�s�˿���" },
						new String[] { "�s�˿��|" }, new String[] { "�s�˿���" } },
				// 6�]��
				new String[][] {
						new String[] { "99��a�]�߬�����", "99��a�]�ߤ�����", "99��a�˫n���_��",
								"99��a�Y��������", "99��a�Y��������", "99��a�b�̤��s��" },
						new String[] { "�]�ߦ�" }, new String[] { "�]�ߦ�" },
						new String[] { "�]�ߦ�" }, new String[] { "�]�ߨ|" },
						new String[] { "�q�]�����D��" } },
				// 7�x��
				new String[][] {
						new String[] { "99��a�x�����橱", "99��a�x���_����", "99��a�x���F�թ�",
								"99��a�x���C����", "99��a�x���j�{��", "99��a�x�����u��",
								"99��a�x���@����", "99��a�x���͹F��", "99��a�x�����쩱",
								"99��a�x�����橱", "99��a�x����l��", "99��a�x���{�ҩ�",
								"99��a�x���~�f��", "99��a�x���j�~��", "99��a�x��������",
								"99��a�x��������", "99��a�x�����F��", "99��a�x���j�[��",
								"99��a�x���R�w��", "99��a�x���M����", "99��a�x��������",
								"99��a�x���_����", "99��a�x���ַ~��", "99��a�j�������",
								"99��a�ӥ����s��", "99��a�j�����\��", "99��a�׭�n����",
								"99��a�x����٩�", "99��a�ӥ��ӥ���", "99��a�j��������",
								"99��a�j���ǩ���", "99��a���p�N�p��", "99��a�j���q����",
								"99��a�x���F����", "99��a���p������", "99��a�x����|��",
								"99��a�Q�餤�s��", "99��a�j�Ҥj�ҩ�", "99��a�x�����q��",
								"99��a�j���ö���", "99��a��Ϥ�Ʃ�", "99��a�׭줤����",
								"99��a�x���F����", "99��a�x���R�R��", "99��a�׭�q�F��" },
						new String[] { "�x����" }, new String[] { "�x����" },
						new String[] { "�x����" }, new String[] { "�x���|" },
						new String[] { "�x����" } },
				// 8�n��
				new String[][] {
						new String[] { "99��a�n��j�P��", "99��a��٤��s��", "99��a�H��������",
								"99��a�n��ˤs��", "99��a�n�뤤����", "99��a�n�������",
								"99��a�W�����n��", "99��a���������" },
						new String[] { "�n���" }, new String[] { "�n���" },
						new String[] { "�n���" }, new String[] { "�n��|" },
						new String[] { "�n���" } },
				// 9����
				new String[][] {
						new String[] { "99��a���ƩM����", "99��a���Ƥ�����", "99��a���ƹ���",
								"99��a���L�U�~��", "99��a���L�R�ש�", "99��a�˴򥭩M��",
								"99��a���ƥФ���", "99��a���ƩM����", "99��a���䤤����",
								"99��a���بq�ǩ�", "99��a���ƪ�©�", "99��a���ƤG�L��",
								"99��a���ƨq����", "99��a���ƪ��Y��", "99��a���ƥùt��",
								"99��a���L�j�P��", "99��a���Ƥ�����", "99��a���ƹ�����",
								"99��a������ک�" }, new String[] { "���Ʀ�" },
						new String[] { "���Ʀ�" }, new String[] { "���Ʀ�" },
						new String[] { "���ƨ|" }, new String[] { "���Ƽ�" } },
				// 10���L
				new String[][] {
						new String[] { "99��a��n������", "99��a�椻���e��", "99��a���L���d��",
								"99��a���L�_�䩱", "99��a�������s��", "99��a����L�˩�",
								"99��a�椻���ͫn��", "99��a�g�w�ذꩱ" },
						new String[] { "���L��" }, new String[] { "���L��" },
						new String[] { "���L��" }, new String[] { "���L�|" },
						new String[] { "���L��" } },
				// 11�Ÿq
				new String[][] {
						new String[] { "99��a�Ÿq���l��", "99��a�Ÿq������", "99��a�Ÿq���W��",
								"99��a�s��^�ѩ�", "99��a�Ÿq���ک�", "99��a�Ÿq���s��",
								"99��a�Ÿq������", "99��a�Ÿq�ͷR��", "99��a�Ÿqnice��",
								"99��a�Ÿq���R��" }, new String[] { "�Ÿq��" },
						new String[] { "�Ÿq��" }, new String[] { "�Ÿq��" },
						new String[] { "�Ÿq�|" }, new String[] { "�Ÿq��" } },
				// 12�x�n
				new String[][] {
						new String[] { "99��a�ñd�n�쩱", "99��a�ñd�p�F��", "99��a�x�n���q��",
								"99��a�ñd�X�s��", "99��a�x�n�h�穱", "99��a�x�n�s�Ʃ�",
								"99��a���w������", "99��a�x�n�s����", "99��a�x�n���تF��",
								"99��a�x�n�k����", "99��a�¨����ة�", "99��a�s����v��",
								"99��a�x�n���Ʃ�", "99��a�x�n�Ψ���", "99��a�n���p�㩱",
								"99��a�x�n�w�n��", "99��a�x�n�F�穱", "99��a�x�n���d��",
								"99��a�x�n�_����", "99��a�x�n�p�_��", "99��a�x�n�إ���",
								"99��a�x�n���\��", "99��a�ñd���ة�", "99��a�x�n�F����" },
						new String[] { "�x�n��" }, new String[] { "�x�n��" },
						new String[] { "�x�n��" }, new String[] { "�x�n�|" },
						new String[] { "�x�n��" } },
				// 13����
				new String[][] {
						new String[] { "99��a��������j��", "99��a���˩�����", "99��a����������",
								"99��a��s���ة�", "99��a��s���ҩ�", "99��a�����L�驱",
								"99��a�������Z��", "99��a�����X�s��", "99��a����X����",
								"99��a�������s��", "99��a�����k����", "99��a���Z������",
								"99��a��s���s��", "99��a�����C�婱", "�֨���������������" },
						new String[] { "������" }, new String[] { "������" },
						new String[] { "������" }, new String[] { "�����|" },
						new String[] { "������" } },
				// 14�̪F
				new String[][] {
						new String[] { "99��a�̪F�D�d��", "99��a�̪F��K��", "99��a�̪F������",
								"99��a�̪F�ﬥ��", "99��a�̪F�M����", "99��a�̪F��{��",
								"99��a�̪F���R��", "99��a�̪F���H��", "99��a�̪F�U����" },
						new String[] { "�̪F��" }, new String[] { "�̪F��" },
						new String[] { "�̪F��" }, new String[] { "�̪F�|" },
						new String[] { "�̪F��" } },
				// 15�x�F
				new String[][] { new String[] { "99��a�x�F���ة�" },
						new String[] { "�x�F��" }, new String[] { "�x�F��" },
						new String[] { "�x�F��" }, new String[] { "�x�F�|" },
						new String[] { "�x�F��" } },
				// 16�Ὤ
				new String[][] {
						new String[] { "99��a�Ὤ���s��", "99��a�Ὤ������", "99��a�Ὤ�M����" },
						new String[] { "�Ὤ��" }, new String[] { "�Ὤ��" },
						new String[] { "�Ὤ��" }, new String[] { "�Ὤ�|" },
						new String[] { "�Ὤ��" } },
				// 17�y��
				new String[][] {
						new String[] { "99��a�y���_����", "99��a�y�����e��", "99��aù�F���v��",
								"99��aù�F���e��", "99��a�Y���C����", "99��a�y���G�˩�" },
						new String[] { "�y����" }, new String[] { "�y����" },
						new String[] { "�y����" }, new String[] { "�y���|" },
						new String[] { "�y����" } },
				// 18���
				new String[][] { new String[] { "99��a��򰨤���" },
						new String[] { "����" }, new String[] { "����" },
						new String[] { "����" }, new String[] { "���|" },
						new String[] { "����" } },
				// 19����
				new String[][] { new String[] { "99��a�������ک�" },
						new String[] { "������" }, new String[] { "������" },
						new String[] { "������" }, new String[] { "�����|" },
						new String[] { "������" } },
				// 20�s��
				new String[][] { new String[] { "�s����" },
						new String[] { "�s����" }, new String[] { "�s����" },
						new String[] { "�s����" }, new String[] { "�s���|" },
						new String[] { "�s����" } } };
		final WheelView wheel2 = (WheelView) findViewById(R.id.wheel2); // �ĤG��
		wheel2.setVisibleItems(5); // �]�w�i���ƥ�
		final WheelView wheel3 = (WheelView) findViewById(R.id.wheel3); // �ĤT��
		wheel3.setVisibleItems(5);

		// �Ĥ@�ӧ��ܺ�ť
		wheel1.addChangingListener(new OnWheelChangedListener() {
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				if (!scrolling) {
					updateCities(wheel2, cities, newValue);
					indexFor1 = newValue;
				}
			}
		});
		// �ĤG�ӧ��ܺ�ť
		wheel2.addChangingListener(new OnWheelChangedListener() {
			public void onChanged(WheelView wheel, int oldValue, int newValue2) {
				if (!scrolling) {
					updateCities2(wheel3, storeName, indexFor1, newValue2);
				}
			}
		});
		// �Ĥ@�Ӻu�ʺ�ť
		wheel1.addScrollingListener(new OnWheelScrollListener() {
			public void onScrollingStarted(WheelView wheel) {
				scrolling = true;
			}

			public void onScrollingFinished(WheelView wheel) {
				scrolling = false;
				updateCities(wheel2, cities, wheel1.getCurrentItem()); // �a�ʲĤG��
				updateCities2(wheel3, storeName, wheel1.getCurrentItem(),// �a�ʲĤT��
						wheel2.getCurrentItem());
				no1 = wheel1.getCurrentItem();
				no2 = wheel2.getCurrentItem();
				no3 = wheel3.getCurrentItem();

			}
		});
		// �ĤG�Ӻu�ʺ�ť
		wheel2.addScrollingListener(new OnWheelScrollListener() {
			public void onScrollingStarted(WheelView wheel) {
				scrolling = true;
			}

			public void onScrollingFinished(WheelView wheel) {
				scrolling = false;
				updateCities2(wheel3, storeName, wheel1.getCurrentItem(),
						wheel2.getCurrentItem());
				no1 = wheel1.getCurrentItem();
				no2 = wheel2.getCurrentItem();
				no3 = wheel3.getCurrentItem();

			}
		});
		// �ĤT�Ӻu�ʺ�ť
		wheel3.addScrollingListener(new OnWheelScrollListener() {
			public void onScrollingStarted(WheelView wheel) {
				scrolling = true;
			}

			public void onScrollingFinished(WheelView wheel) {
				scrolling = false;
				no1 = wheel1.getCurrentItem();
				no2 = wheel2.getCurrentItem();
				no3 = wheel3.getCurrentItem();

			}
		});
		wheel1.setCurrentItem(1);
		wheel2.setCurrentItem(0);
		wheel3.setCurrentItem(0);
	}

	/**
	 * Updates the city wheel
	 */
	// �Ĥ@�ӧ��ܱa�ʲĤG��
	private void updateCities(WheelView city, String cities[][], int index) {
		ArrayWheelAdapter<String> adapter = new ArrayWheelAdapter<String>(this,
				cities[index]);
		adapter.setTextSize(18);
		city.setViewAdapter(adapter);
		city.setCurrentItem(cities[index].length / 2); // �a�ʫ᪺�_�l��m

	}

	// �ĤG�ӧ��ܱa�ʲĤT��
	private void updateCities2(WheelView city, String cities[][][], int index,
			int index2) {
		ArrayWheelAdapter<String> adapter = new ArrayWheelAdapter<String>(this,
				cities[index][index2]);
		adapter.setTextSize(18);
		city.setViewAdapter(adapter);
		city.setCurrentItem(cities[index][index2].length / 2);// �a�ʫ᪺�_�l��m

	}

	/**
	 * Adapter for countries
	 */
	private class CountryAdapter extends AbstractWheelTextAdapter {
		// Countries names
		private String countries[] = // �Ĥ@�Ӧr
		new String[] { "�򶩥�", "�x�_��", "�s�_��", "��鿤��", "�s�˥�", "�s�˿�", "�]�߿�", "�x����",
				"�n�뿤��", "���ƿ���", "���L����", "�Ÿq����", "�x�n��", "������", "�̪F����", "�x�F����",
				"�Ὤ����", "�y������", "���", "������", "�s����" };

		// Countries flags

		/**
		 * Constructor
		 */
		protected CountryAdapter(Context context) {
			super(context, R.layout.country_layout, NO_RESOURCE);

			setItemTextResource(R.id.country_name);
		}

		@Override
		public int getItemsCount() {
			return countries.length;
		}

		@Override
		protected CharSequence getItemText(int index) {
			return countries[index];
		}
	}

	// �e�X
	void goToGoogleMap() {
		Intent intent = new Intent(this, GoToGoogleMap.class);
		intent.putExtra("check", "wheel");
		intent.putExtra("no1", no1);
		intent.putExtra("no2", no2);
		intent.putExtra("no3", no3);
		startActivity(intent);
	}
}
