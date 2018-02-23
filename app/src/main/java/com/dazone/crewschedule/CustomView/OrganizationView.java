package com.dazone.crewschedule.CustomView;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dazone.crewschedule.Dtos.ErrorDto;
import com.dazone.crewschedule.Dtos.PersonData;
import com.dazone.crewschedule.Interfaces.OnGetAllOfUser;
import com.dazone.crewschedule.Interfaces.OnOrganizationSelectedEvent;
import com.dazone.crewschedule.R;
import com.dazone.crewschedule.Utils.ImageUtils;
import com.dazone.crewschedule.Utils.Utils;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Sherry on 12/31/15.
 */
public class OrganizationView {
    int i = 0;
    String TAG = "OrganizationView";
    private ArrayList<PersonData> mPersonList = new ArrayList<>();
    private ArrayList<PersonData> mSelectedPersonList;
    private Context mContext;
    private int displayType = 0; // 0 folder structure , 1
    private OnOrganizationSelectedEvent mSelectedEvent;

    public OrganizationView(Context context, ArrayList<PersonData> selectedPersonList, boolean isDisplaySelectedOnly, ViewGroup viewGroup) {
        this.mContext = context;
        if (selectedPersonList != null)
            this.mSelectedPersonList = selectedPersonList;
        else
            this.mSelectedPersonList = new ArrayList<>();
        if (isDisplaySelectedOnly) {
            initSelectedList(selectedPersonList, viewGroup);
        } else {
            initWholeOrganization(viewGroup);
        }
    }

    /**
     * this function automatically set all to selected
     *
     * @param selectedPersonList
     */
    private void initSelectedList(ArrayList<PersonData> selectedPersonList, ViewGroup viewGroup) {
        mPersonList = new ArrayList<>();
        for (PersonData selectedPerson : selectedPersonList) {
            selectedPerson.setIsCheck(true);
            if (selectedPerson.getType() == 2) {
                mPersonList.add(selectedPerson);
            }
        }
        createRecursiveList(mPersonList, mPersonList);
        displayAsFolder(viewGroup);
    }

    private void initWholeOrganization(final ViewGroup viewGroup) {
        PersonData.getDepartmentAndUser(new OnGetAllOfUser() {
            @Override
            public void onGetAllOfUserSuccess(ArrayList<PersonData> list) {
                mPersonList = new ArrayList<>(list);
                // set selected for list before create recursive list
                for (PersonData selectedPerson : mSelectedPersonList) {
                    int indexOf = mPersonList.indexOf(selectedPerson);
                    if (indexOf != -1) {
                        mPersonList.get(indexOf).setIsCheck(true);
                    }
                }
                createRecursiveList(list, mPersonList);
                displayAsFolder(viewGroup);
            }

            @Override
            public void onGetAllOfUserFail(ErrorDto errorData) {

            }
        });
    }

    public void setOnSelectedEvent(OnOrganizationSelectedEvent selectedEvent) {
        this.mSelectedEvent = selectedEvent;
    }

    private void createRecursiveList(ArrayList<PersonData> list, ArrayList<PersonData> parentList) {

        //create recursive list
        Iterator<PersonData> iter = list.iterator();
        while (iter.hasNext()) {
            PersonData tempPerson = iter.next();
            for (PersonData person : parentList) {
                if (person.getType() == 1) {
                    if (tempPerson.getType() == 1 && person.getDepartNo() == tempPerson.getDepartmentParentNo()) {
//                         department compare by departNo and parentNo
                        person.addChild(tempPerson);
                        iter.remove();
                        parentList.remove(tempPerson);
                        break;
                    } else if (tempPerson.getType() == 2 && person.getDepartNo() == tempPerson.getDepartNo()) {
                        // member , compare by departNo and departNo
                        person.addChild(tempPerson);
                        iter.remove();
                        parentList.remove(tempPerson);
                        break;
                    }
                    if (person.getPersonList() != null && person.getPersonList().size() > 0) {
                        // not in root list , search in child list
                        ArrayList<PersonData> test = new ArrayList<>();
                        test.add(tempPerson);
                        createRecursiveList(test, person.getPersonList());
                    }
                }
            }
        }
    }


    public void setDisplayType(int type) {
        this.displayType = type;
    }

    public void displayAsFolder(ViewGroup viewGroup) {
        this.displayType = 0;
        for (PersonData personData : mPersonList) {
            if (personData.getPersonList() != null && personData.getType() != 2 && personData.getDepartmentParentNo() == 0) {
                draw(personData, viewGroup, false, 0);
            }
        }
    }

    public void displayMatchQuery(ViewGroup viewGroup, String query) {
        ArrayList<PersonData> resultList = new ArrayList<>();
        getPersonDataWithQuery(query, resultList, mPersonList);
        for (PersonData personData : resultList) {
            draw(personData, viewGroup, false, 0);
        }
    }

    private void getPersonDataWithQuery(String query, ArrayList<PersonData> searchResultList, ArrayList<PersonData> searchList) {
        for (PersonData personData : searchList) {
            if (personData.getType() == 2) {
                if ((personData.getFullName() != null && personData.getFullName().toLowerCase().contains(query))
                        || (personData.getEmail() != null && personData.getEmail().toLowerCase().contains(query))) {
                    searchResultList.add(personData);

                }
            }
            if (personData.getPersonList() != null && personData.getPersonList().size() > 0) {
                getPersonDataWithQuery(query, searchResultList, personData.getPersonList());
            }
        }
    }

    private void draw(final PersonData personData, final ViewGroup layout, final boolean checked, final int iconMargin) {
        final LinearLayout child_list;
        final ImageView folderIcon;
        final ImageView avatar;
        final TextView name;
        final CheckBox row_check;
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_organization, null);
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        layout.addView(view);

        child_list = (LinearLayout) view.findViewById(R.id.child_list);
        folderIcon = (ImageView) view.findViewById(R.id.icon);
        avatar = (ImageView) view.findViewById(R.id.avatar);
        name = (TextView) view.findViewById(R.id.name);
        row_check = (CheckBox) view.findViewById(R.id.row_check);
        row_check.setChecked(personData.isCheck());

        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) folderIcon.getLayoutParams();
        LinearLayout.LayoutParams params_av = new LinearLayout.LayoutParams(Utils.getDimenInPx(R.dimen.size_img_custom), Utils.getDimenInPx(R.dimen.size_img_custom));
        if (displayType == 0) // set margin for icon if it's company type
        {
            params.leftMargin = iconMargin;
            params_av.leftMargin = iconMargin;
        }
        folderIcon.setLayoutParams(params);
        avatar.setLayoutParams(params_av);
       /* if(personData.getUserNo()==UserData.getUserInformation().getId())
        {
            row_check.setChecked(false);
            row_check.setEnabled(false);
        }*/
        String nameString = personData.getFullName();
        if (personData.getType() == 2) {
            nameString += !TextUtils.isEmpty(personData.getPositionName()) ? "\n" + personData.getPositionName() : "";
            ImageUtils.showImage(personData, avatar);
            folderIcon.setVisibility(View.GONE);
        } else {
            avatar.setVisibility(View.GONE);
            folderIcon.setVisibility(View.VISIBLE);
        }
        i++;
//        if (i < 9) {
//            avatar.setVisibility(View.GONE);
//            folderIcon.setVisibility(View.GONE);
//            name.setVisibility(View.GONE);
//            row_check.setVisibility(View.GONE);
//        }
        name.setText(nameString);

        final int tempMargin = iconMargin + Utils.getDimenInPx(R.dimen.activity_login_user_margin);
        row_check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked && personData.getType() == 2) {
//                    unCheckFather(dto);
                    ViewGroup parent = ((ViewGroup) layout.getParent());
                    unCheckBoxParent(parent);
                } else {
                    if (buttonView.getTag() != null && !(Boolean) buttonView.getTag()) {
                        buttonView.setTag(true);
                    } else {
                        personData.setIsCheck(isChecked);
                        if (personData.getPersonList() != null && personData.getPersonList().size() != 0) {
                            int index = 0;
                            for (PersonData dto1 : personData.getPersonList()) {

                                dto1.setIsCheck(isChecked);
                                View childView = child_list.getChildAt(index);
                                CheckBox childCheckBox = (CheckBox) childView.findViewById(R.id.row_check);
                                if (childCheckBox != null) {
                                    if (childCheckBox.isEnabled()) {
                                        childCheckBox.setChecked(dto1.isCheck());
                                    }

                                } else {
                                    break;
                                }
                                index++;
                            }
                        }
                    }
                }
                if (mSelectedEvent != null) {
                    mSelectedEvent.onOrganizationCheck(isChecked, personData);
                }
            }
        });
        if (personData.getPersonList() != null && personData.getPersonList().size() != 0) {
            folderIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showHideSubMenuView(child_list, folderIcon);
                }
            });
            name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showHideSubMenuView(child_list, folderIcon);
                }
            });

            for (PersonData dto1 : personData.getPersonList()) {
                draw(dto1, child_list, false, tempMargin);
            }
//            for (int i = 0; i < personData.getPersonList().size(); i++) {
//                draw(personData.getPersonList().get(i), child_list, false, tempMargin);
//            }
        }
    }

    private void unCheckBoxParent(ViewGroup view) {
        if (view.getId() == R.id.item_org_main_wrapper || view.getId() == R.id.item_org_wrapper) {
            CheckBox parentCheckBox = (CheckBox) view.findViewById(R.id.row_check);
            if (parentCheckBox.isChecked()) {
                parentCheckBox.setTag(false);
                parentCheckBox.setChecked(false);
            }
            if ((view.getParent()).getParent() instanceof ViewGroup) {
                try {
                    ViewGroup parent = (ViewGroup) (view.getParent()).getParent();
                    unCheckBoxParent(parent);
                } catch (Exception e) {
                }
            }
        }
    }

    private void showHideSubMenuView(LinearLayout child_list, ImageView icon) {
        if (child_list.getVisibility() == View.VISIBLE) {
            child_list.setVisibility(View.GONE);
            icon.setImageResource(R.drawable.ic_folder_close);

        } else {
            child_list.setVisibility(View.VISIBLE);
            icon.setImageResource(R.drawable.ic_folder_open);

        }
    }
}
