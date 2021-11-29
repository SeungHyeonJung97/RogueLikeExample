package com.example.roguelikeexample

import android.annotation.SuppressLint
import android.content.Context
import android.opengl.Visibility
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.roguelikeexample.BagAdapter.MyViewHolder
import com.example.roguelikeexample.Settings.HeroArmor
import com.example.roguelikeexample.Settings.HeroCurrentHP
import com.example.roguelikeexample.Settings.HeroMaxHP
import com.example.roguelikeexample.Settings.HeroPower
import com.example.roguelikeexample.databinding.ListItemBinding
import com.example.roguelikeexample.Settings.advancedPotion
import com.example.roguelikeexample.Settings.armor
import com.example.roguelikeexample.Settings.haveItem
import com.example.roguelikeexample.Settings.isWornArmor
import com.example.roguelikeexample.Settings.isWornWeapon
import com.example.roguelikeexample.Settings.potion
import com.example.roguelikeexample.Settings.weapon
import com.example.roguelikeexample.Settings.wornArmor
import com.example.roguelikeexample.Settings.wornWeapon
import kotlinx.android.synthetic.main.activity_status.*
import kotlinx.android.synthetic.main.list_item.view.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.noButton
import org.jetbrains.anko.yesButton
import kotlin.random.Random

class BagAdapter(val context: StatusActivity) : RecyclerView.Adapter<MyViewHolder>() {

    var data = haveItem() //리사이클러뷰에서 사용할 데이터 미리 정의 -> 나중에 MainActivity등에서 datalist에 실제 데이터 추가
    var noItem = false
    var itemNumber = 0

    inner class MyViewHolder(private val binding: ListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("NotifyDataSetChanged")
        fun bind(item: String) {
            Log.d("countdata", "${data}")

            if (item.equals("advancedPotion")) {
                binding.ivItem.setImageResource(R.drawable.advancedpotion)
                binding.tvItemName.text = "고급 포션"
                binding.tvItemLabel.text = "체력 + 60"
                binding.tvItemNumber.text = binding.root.resources.getString(
                    R.string.number,
                    advancedPotion
                )
                binding.btnEnforce.visibility = View.INVISIBLE
            }

            if (item.equals("potion")) {
                binding.ivItem.setImageResource(R.drawable.potion)
                binding.tvItemName.text = "포션"
                binding.tvItemLabel.text = "체력 + 30"
                binding.tvItemNumber.text = binding.root.resources.getString(
                    R.string.number,
                    potion
                )
                binding.btnEnforce.visibility = View.INVISIBLE
            }

            if (item.equals("weapon0")) {
                binding.ivItem.setImageResource(R.drawable.weapon1)
                binding.tvItemName.text = "몽둥이"
                binding.tvItemLabel.text = "공격력 + 5"
                binding.tvItemNumber.text = binding.root.resources.getString(
                    R.string.number,
                    weapon[0]
                )
            }
            if (item.equals("weapon1")) {
                binding.ivItem.setImageResource(R.drawable.weapon2)
                binding.tvItemName.text = "롱소드"
                binding.tvItemLabel.text = "공격력 + 10"
                binding.tvItemNumber.text = binding.root.resources.getString(
                    R.string.number,
                    weapon[1]
                )
            }
            if (item.equals("weapon2")) {
                binding.ivItem.setImageResource(R.drawable.weapon3)
                binding.tvItemName.text = "레이피어"
                binding.tvItemLabel.text = "공격력 + 25"
                binding.tvItemNumber.text = binding.root.resources.getString(
                    R.string.number,
                    weapon[2]
                )
            }
            if (item.equals("weapon3")) {
                binding.ivItem.setImageResource(R.drawable.weapon4)
                binding.tvItemName.text = "글라디우스"
                binding.tvItemLabel.text = "공격력 + 50"
                binding.tvItemNumber.text = binding.root.resources.getString(
                    R.string.number,
                    weapon[3]
                )
            }

            if (item.equals("armor0")) {
                binding.ivItem.setImageResource(R.drawable.armor1)
                binding.tvItemName.text = "천 갑옷"
                binding.tvItemLabel.text = "방어력 + 3"
                binding.tvItemNumber.text = binding.root.resources.getString(
                    R.string.number,
                    armor[0]
                )
            }
            if (item.equals("armor1")) {
                binding.ivItem.setImageResource(R.drawable.armor2)
                binding.tvItemName.text = "워리어 슈트"
                binding.tvItemLabel.text = "방어력 + 5"

                binding.tvItemNumber.text = binding.root.resources.getString(
                    R.string.number,
                    armor[1]
                )
            }
            if (item.equals("armor2")) {
                binding.ivItem.setImageResource(R.drawable.armor3)
                binding.tvItemName.text = "파수꾼의 갑옷"
                binding.tvItemLabel.text = "방어력 + 10"

                binding.tvItemNumber.text = binding.root.resources.getString(
                    R.string.number,
                    armor[2]
                )
            }
            if (item.equals("armor3")) {
                binding.ivItem.setImageResource(R.drawable.armor4)
                binding.tvItemName.text = "태양불꽃 망토"
                binding.tvItemLabel.text = "방어력 + 18"
                binding.tvItemNumber.text = binding.root.resources.getString(
                    R.string.number,
                    armor[3]
                )
            }

            if (item.equals("noItem")) {
                binding.tvNoItems.visibility = View.VISIBLE
                binding.clItem.visibility = View.GONE
            } else {
                binding.clItem.visibility = View.VISIBLE
                binding.tvNoItems.visibility = View.GONE

                itemView.setOnClickListener {
                    var title = ""
                    var message = ""
                    var text = ""

                    if (data[position].lowercase().contains("potion")) {
                        title = "아이템 사용"
                        message = "아이템을 사용하시겠습니까 ?"
                        text = "아이템이 사용되었습니다."
                    } else {
                        title = "아이템 착용"
                        message = "아이템을 착용하시겠습니까 ?"
                        text = "아이템이 착용되었습니다."
                    }
                    itemView.context.alert(message, title) {
                        yesButton {
                            Log.d("position", data[position])
                            when (data[position]) {
                                "potion" -> {
                                    if (HeroCurrentHP == HeroMaxHP) {
                                        Toast.makeText(
                                            context,
                                            "더 이상 회복할 체력이 없습니다.",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    } else {
                                        Settings.potion--
                                        HeroCurrentHP += 30
                                        if (HeroCurrentHP > HeroMaxHP) {
                                            HeroCurrentHP = HeroMaxHP
                                        }
                                        context.tv_status_hp.text = context.resources.getString(
                                            R.string.status_hp,
                                            HeroCurrentHP,
                                            HeroMaxHP
                                        )
                                        itemNumber = potion
                                        if (itemNumber.isZero()) {
                                            data.remove(data[position])
                                        }
                                        Toast.makeText(itemView.context, text, Toast.LENGTH_SHORT)
                                            .show()
                                    }

                                }
                                "advancedPotion" -> {
                                    if (HeroCurrentHP == HeroMaxHP) {
                                        Toast.makeText(
                                            context,
                                            "더 이상 회복할 체력이 없습니다.",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    } else {
                                        Settings.advancedPotion--
                                        HeroCurrentHP += 60
                                        if (HeroCurrentHP > HeroMaxHP) {
                                            HeroCurrentHP = HeroMaxHP
                                        }
                                        context.tv_status_hp.text = context.resources.getString(
                                            R.string.status_hp,
                                            HeroCurrentHP,
                                            HeroMaxHP
                                        )
                                        itemNumber = advancedPotion
                                        if (itemNumber.isZero()) {
                                            data.remove(data[position])
                                        }
                                        Toast.makeText(itemView.context, text, Toast.LENGTH_SHORT)
                                            .show()
                                    }
                                }
                                "weapon0" -> {
                                    if (isWornWeapon != null) {
                                        Toast.makeText(
                                            context,
                                            "이미 장비를 착용하고 있습니다.",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    } else {
                                        Settings.weapon[0]--
                                        isWornWeapon = 0
                                        wornWeapon = 5
                                        Settings.HeroPower += wornWeapon
                                        itemNumber = weapon[0]
                                        context.tv_status_weapon.text = context.resources.getString(
                                            R.string.status_power,
                                            HeroPower
                                        )
                                        context.iv_weapon.setImageResource(R.drawable.weapon1)
                                        if (itemNumber.isZero()) {
                                            data.remove(data[position])
                                        }
                                        Toast.makeText(itemView.context, text, Toast.LENGTH_SHORT)
                                            .show()
                                    }
                                }
                                "weapon1" -> {
                                    if (isWornWeapon != null) {
                                        Toast.makeText(
                                            context,
                                            "이미 장비를 착용하고 있습니다.",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    } else {
                                        Settings.weapon[1]--
                                        isWornWeapon = 1
                                        wornWeapon = 10
                                        HeroPower += wornWeapon
                                        itemNumber = weapon[1]
                                        context.tv_status_weapon.text = context.resources.getString(
                                            R.string.status_power,
                                            HeroPower
                                        )
                                        context.iv_weapon.setImageResource(R.drawable.weapon2)
                                        if (itemNumber.isZero()) {
                                            data.remove(data[position])
                                        }
                                        Toast.makeText(itemView.context, text, Toast.LENGTH_SHORT)
                                            .show()
                                    }
                                }
                                "weapon2" -> {
                                    if (isWornWeapon != null) {
                                        Toast.makeText(
                                            context,
                                            "이미 장비를 착용하고 있습니다.",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    } else {
                                        Settings.weapon[2]--
                                        isWornWeapon = 2
                                        wornWeapon = 25
                                        itemNumber = weapon[2]
                                        HeroPower += wornWeapon
                                        context.tv_status_weapon.text = context.resources.getString(
                                            R.string.status_power,
                                            HeroPower
                                        )
                                        context.iv_weapon.setImageResource(R.drawable.weapon3)
                                        if (itemNumber.isZero()) {
                                            data.remove(data[position])
                                        }
                                        Toast.makeText(itemView.context, text, Toast.LENGTH_SHORT)
                                            .show()
                                    }
                                }
                                "weapon3" -> {
                                    if (isWornWeapon != null) {
                                        Toast.makeText(
                                            context,
                                            "이미 장비를 착용하고 있습니다.",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    } else {
                                        Settings.weapon[3]--
                                        isWornWeapon = 3
                                        wornWeapon = 50
                                        HeroPower += wornWeapon
                                        itemNumber = weapon[3]
                                        context.tv_status_weapon.text = context.resources.getString(
                                            R.string.status_power,
                                            HeroPower
                                        )
                                        context.iv_weapon.setImageResource(R.drawable.weapon4)
                                        if (itemNumber.isZero()) {
                                            data.remove(data[position])
                                        }
                                        Toast.makeText(itemView.context, text, Toast.LENGTH_SHORT)
                                            .show()
                                    }
                                }
                                "armor0" -> {
                                    if (isWornArmor != null) {
                                        Toast.makeText(
                                            context,
                                            "이미 장비를 착용하고 있습니다.",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    } else {
                                        Settings.armor[0]--
                                        isWornArmor = 0
                                        wornArmor = 3
                                        HeroArmor += wornArmor
                                        itemNumber = armor[0]
                                        context.tv_status_armor.text = context.resources.getString(
                                            R.string.status_armor,
                                            HeroArmor
                                        )
                                        context.iv_armor.setImageResource(R.drawable.armor1)
                                        if (itemNumber.isZero()) {
                                            data.remove(data[position])
                                        }
                                        Toast.makeText(itemView.context, text, Toast.LENGTH_SHORT)
                                            .show()
                                    }
                                }
                                "armor1" -> {
                                    if (isWornArmor != null) {
                                        Toast.makeText(
                                            context,
                                            "이미 장비를 착용하고 있습니다.",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    } else {
                                        Settings.armor[1]--
                                        isWornArmor = 1
                                        wornArmor = 5
                                        HeroArmor += wornArmor
                                        itemNumber = armor[1]
                                        context.tv_status_armor.text = context.resources.getString(
                                            R.string.status_armor,
                                            HeroArmor
                                        )
                                        context.iv_armor.setImageResource(R.drawable.armor2)
                                        if (itemNumber.isZero()) {
                                            data.remove(data[position])
                                        }
                                        Toast.makeText(itemView.context, text, Toast.LENGTH_SHORT)
                                            .show()
                                    }
                                }
                                "armor2" -> {
                                    if (isWornArmor != null) {
                                        Toast.makeText(
                                            context,
                                            "이미 장비를 착용하고 있습니다.",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    } else {
                                        Settings.armor[2]--
                                        isWornArmor = 2
                                        wornArmor = 10
                                        HeroArmor += wornArmor
                                        itemNumber = armor[2]
                                        context.tv_status_armor.text = context.resources.getString(
                                            R.string.status_armor,
                                            HeroArmor
                                        )
                                        context.iv_armor.setImageResource(R.drawable.armor3)
                                        if (itemNumber.isZero()) {
                                            data.remove(data[position])
                                        }
                                        Toast.makeText(itemView.context, text, Toast.LENGTH_SHORT)
                                            .show()
                                    }
                                }
                                "armor3" -> {
                                    if (isWornArmor != null) {
                                        Toast.makeText(
                                            context,
                                            "이미 장비를 착용하고 있습니다.",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    } else {
                                        Settings.armor[3]--
                                        isWornArmor = 3
                                        wornArmor = 18
                                        HeroArmor += wornArmor
                                        itemNumber = armor[3]
                                        context.tv_status_armor.text = context.resources.getString(
                                            R.string.status_armor,
                                            HeroArmor
                                        )
                                        context.iv_armor.setImageResource(R.drawable.armor4)
                                        if (itemNumber.isZero()) {
                                            data.remove(data[position])
                                        }
                                        Toast.makeText(itemView.context, text, Toast.LENGTH_SHORT)
                                            .show()
                                    }
                                }
                            }
                            if (isWornArmor != null) context.iv_armor.isClickable = true
                            if (isWornWeapon != null) context.iv_weapon.isClickable = true
                            this@BagAdapter.notifyDataSetChanged()
                        }
                        noButton {}
                        isCancelable = false
                    }.show()
                    Log.d("position", "${getItemId()}")
                }
            }

            binding.btnEnforce.setOnClickListener {
                val number = binding.tvItemNumber.text.split(" ")[0]
                Log.d("position", "${number}")
                if (number.toInt() >= 2) {
                    itemView.context.alert("정말 강화하시겠습니까?\n실패시 아이템이 파괴됩니다.", "강화") {
                        yesButton {
                            val success = Random.nextInt(10)
                            if (!item.equals("글라디우스") && !item.equals("태양불꽃 망토")) {
                                if (success >= 4) {
                                    when (binding.tvItemName.text.toString()) {
                                        "몽둥이" -> {
                                            weapon[0] -= 2
                                            weapon[1] += 1

                                        }
                                        "롱소드" -> {
                                            weapon[1] -= 2
                                            weapon[2] += 1
                                        }
                                        "레이피어" -> {
                                            weapon[2] -= 2
                                            weapon[3] += 1
                                        }

                                        "천 갑옷" -> {
                                            armor[0] -= 2
                                            armor[1] += 1
                                        }
                                        "워리어 슈트" -> {
                                            armor[1] -= 2
                                            armor[2] += 1
                                        }
                                        "파수꾼의 갑옷" -> {
                                            armor[2] -= 2
                                            armor[3] += 1
                                        }
                                    }
                                    Toast.makeText(
                                        itemView.context,
                                        "강화에 성공하셨습니다 !",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                } else {
                                    Toast.makeText(
                                        itemView.context,
                                        "아이템이 파괴되었습니다.",
                                        Toast.LENGTH_SHORT
                                    )
                                        .show()
                                    when (binding.tvItemName.text.toString()) {
                                        "몽둥이" -> {
                                            weapon[0] -= 2
                                        }
                                        "롱소드" -> {
                                            weapon[1] -= 2
                                        }
                                        "레이피어" -> {
                                            weapon[2] -= 2
                                        }

                                        "천 갑옷" -> {
                                            armor[0] -= 2
                                        }
                                        "워리어 슈트" -> {
                                            armor[1] -= 2
                                        }
                                        "파수꾼의 갑옷" -> {
                                            armor[2] -= 2
                                        }
                                    }
                                }
                            } else {
                                Toast.makeText(
                                    itemView.context,
                                    "최종 장비는 강화하실 수 없습니다 !",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                            data = haveItem()
                            this@BagAdapter.notifyDataSetChanged()
                        }
                        noButton { }
                        isCancelable = false
                    }.show()
                } else {
                    Toast.makeText(
                        itemView.context,
                        "장비가 2개이상 필요합니다 !",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }


    //만들어진 뷰홀더 없을때 뷰홀더(레이아웃) 생성하는 함수
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding =
            ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        if (data.size.isZero()) {
            noItem = true
            return 1
        }
        return data.size
    }

    //recyclerview가 viewholder를 가져와 데이터 연결할때 호출
//적절한 데이터를 가져와서 그 데이터를 사용하여 뷰홀더의 레이아웃 채움
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        if (data.isEmpty()) {
            holder.bind("noItem")
        } else {
            holder.bind(data[position])
        }
    }
}