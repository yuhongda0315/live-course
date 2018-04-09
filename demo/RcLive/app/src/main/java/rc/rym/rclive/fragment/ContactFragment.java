package rc.rym.rclive.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;

import rc.rym.rclive.R;
import rc.rym.rclive.widget.ContactItemView;


public class ContactFragment extends Fragment {

    ListView contactList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.main_fragment_contact, container, false);
        contactList = root.findViewById(R.id.contact_list);
        contactList.setAdapter(new ContactListAdapter());
        return root;
    }

    private class ContactListAdapter extends BaseAdapter {
        private LayoutInflater inflater;
        private ContactItemView newFriendItem;

        ContactListAdapter() {
            inflater = ContactFragment.this.getActivity().getLayoutInflater();
            newFriendItem = new ContactItemView(ContactFragment.this.getContext());
            newFriendItem.setInfoMode(ContactItemView.SIMPLE);
            newFriendItem.setNickname("新的朋友");
            newFriendItem.setPortrait(R.mipmap.new_friend);
            newFriendItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final View newFriendDlg = inflater.inflate(R.layout.dialog_open_chat, null);
                    new AlertDialog.Builder(ContactFragment.this.getContext()).setTitle("添加好友").setView(newFriendDlg)
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    String targetId = ((EditText) newFriendDlg.findViewById(R.id.edit_chat_to)).getText().toString();
                                }
                            }).setNegativeButton("取消", null).show();
                }
            });
        }

        @Override
        public int getCount() {
            return 1;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ContactItemView itemView;
            if (position == 0) {
                itemView = newFriendItem;
            } else if (convertView == null) {
                itemView = new ContactItemView(ContactFragment.this.getContext());
                itemView.setTag(itemView);
            } else {
                itemView = (ContactItemView) convertView.getTag();
            }
            return itemView;
        }
    }
}
